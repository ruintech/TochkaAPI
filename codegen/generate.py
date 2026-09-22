#!/usr/bin/env python3
"""Generates Java models and service classes from the Tochka Bank OpenAPI specification.

Usage:  python3 codegen/generate.py [path/to/swagger.json]

Only the com.tochka.api.model and com.tochka.api.api packages are overwritten; the
hand-written client core (http, auth, webhook, tls) is never touched.

Text taken from the specification (field titles, method descriptions, examples) is copied
into the generated javadoc as is, so it stays in Russian — that is how the bank writes it.
"""

import json
import os
import re
import shutil
import sys
from collections import OrderedDict

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
SPEC = sys.argv[1] if len(sys.argv) > 1 else os.path.join(ROOT, "swagger.json")
JAVA_ROOT = os.path.join(ROOT, "src", "main", "java", "com", "tochka", "api")
MODEL_DIR = os.path.join(JAVA_ROOT, "model")
API_DIR = os.path.join(JAVA_ROOT, "api")
MODEL_PKG = "com.tochka.api.model"
API_PKG = "com.tochka.api.api"

JAVA_KEYWORDS = {
    "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const",
    "continue", "default", "do", "double", "else", "enum", "extends", "final", "finally", "float",
    "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native",
    "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp",
    "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void",
    "volatile", "while", "true", "false", "null", "record", "var", "yield",
}

# Schemas whose specification names carry internal module prefixes of the bank's backend.
SCHEMA_NAME_OVERRIDES = {
    "application__invoice__models__enums__PaymentStatusEnum": "InvoicePaymentStatusEnum",
    "application__sbp__models__enums__PaymentStatusEnum": "SbpQrCodePaymentStatusEnum",
    "application__open_banking__models__external_models__AccountListResponseModel":
        "OpenBankingAccountListResponseModel",
    "application__sbp__models__response_models__sbp__AccountListResponseModel":
        "SbpAccountListResponseModel",
    "common__errors_models__Something_going_wrongResponse__1": "DependencyFailureResponse",
    "common__errors_models__Something_going_wrongResponse__2": "ForbiddenErrorResponse",
    "ReceiptItemModel-Input": "ReceiptItemModel",
    "ReceiptItemModel-Output": "ReceiptItemOutputModel",
}

# OpenAPI tag -> service class name and its javadoc summary.
TAG_TO_API = OrderedDict([
    ("Работа со счетами", ("AccountsApi", "Company accounts: list and details.")),
    ("Работа с балансами счетов", ("BalancesApi", "Account balances and authorized card transactions.")),
    ("Работа с выписками", ("StatementsApi", "Account statements: ordering, retrieval and listing.")),
    ("Работа с клиентами", ("CustomersApi", "Companies connected to your API access.")),
    ("Работа с платежами", ("PaymentsApi", "Payment orders: creating a payment for signing and checking its status.")),
    ("Работа с платёжными ссылками", ("AcquiringApi", "Internet acquiring: payment links, refunds, registry and retailers.")),
    ("Работа с подписками", ("SubscriptionsApi", "Subscriptions (recurring card payments).")),
    ("Работа с выставлением счетов", ("InvoicesApi", "Invoices for companies and sole proprietors.")),
    ("Работа с закрывающими документами", ("ClosingDocumentsApi", "Closing documents: acts, packing lists, invoices and UPD.")),
    ("Работа с вебхуками", ("WebhooksApi", "Webhook subscriptions for account and payment events.")),
    ("Работа с разрешениями", ("ConsentsApi", "Consents (permission lists) used by OAuth 2.0 authorization.")),
    ("Сервис СБП: Работа с ЮЛ", ("SbpLegalEntitiesApi", "SBP: legal entity registration and its accounts.")),
    ("Сервис СБП: Работа с ТСП", ("SbpMerchantsApi", "SBP: merchants (points of sale).")),
    ("Сервис СБП: Работа с QR-кодами", ("SbpQrCodesApi", "SBP: static and dynamic QR codes.")),
    ("Сервис СБП: Работа с кассовыми QR-кодами", ("SbpCashboxQrCodesApi", "SBP: cashbox QR codes — one code, many payments, reactivated before each one.")),
    ("Сервис СБП: работа с B2B QR-кодами", ("SbpB2bQrCodesApi", "SBP: B2B QR codes for payments from companies and sole proprietors.")),
    ("Сервис СБП: Работа с возвратами", ("SbpRefundsApi", "SBP: refunds of payments accepted via QR codes.")),
])

# Path parameter name -> Java type other than String, plus how to render it as a string.
PATH_PARAM_OVERRIDES = {
    "qrcIds": ("java.util.List<String>", 'String.join(",", {name})'),
}

CYRILLIC = {
    "а": "a", "б": "b", "в": "v", "г": "g", "д": "d", "е": "e", "ё": "e", "ж": "zh", "з": "z",
    "и": "i", "й": "y", "к": "k", "л": "l", "м": "m", "н": "n", "о": "o", "п": "p", "р": "r",
    "с": "s", "т": "t", "у": "u", "ф": "f", "х": "h", "ц": "ts", "ч": "ch", "ш": "sh",
    "щ": "sch", "ъ": "", "ы": "y", "ь": "", "э": "e", "ю": "yu", "я": "ya",
}


def transliterate(text):
    out = []
    for ch in text:
        lower = ch.lower()
        if lower in CYRILLIC:
            replacement = CYRILLIC[lower]
            out.append(replacement.upper() if ch.isupper() else replacement)
        else:
            out.append(ch)
    return "".join(out)


def pascal(name):
    parts = re.split(r"[^0-9a-zA-Z]+", transliterate(name))
    return "".join(p[:1].upper() + p[1:] for p in parts if p)


def camel(name):
    result = pascal(name)
    if not result:
        return "value"
    # Keep acronyms such as URL or ID intact: only the first letter is lowercased.
    result = result[0].lower() + result[1:]
    if result in JAVA_KEYWORDS:
        result += "Value"
    if result[0].isdigit():
        result = "v" + result
    return result


def enum_constant(value):
    text = transliterate(str(value))
    text = re.sub(r"([a-z0-9])([A-Z])", r"\1_\2", text)
    text = re.sub(r"[^0-9a-zA-Z]+", "_", text).strip("_").upper()
    if not text:
        text = "EMPTY"
    if text[0].isdigit():
        text = "V" + text
    if text in JAVA_KEYWORDS:
        text += "_VALUE"
    return text


def javadoc_escape(text):
    if not text:
        return ""
    text = text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
    text = text.replace("*/", "*&#47;").replace("@", "&#64;")
    text = " ".join(text.split())
    # Convert the markdown used in specification descriptions into javadoc markup.
    text = re.sub(r"\[([^\]]+)\]\(([^)]+)\)", r"\1 (\2)", text)
    text = re.sub(r"`([^`]+)`", r"{&#64;code \1}", text)
    text = text.replace("{&#64;code ", "{@code ")
    text = re.sub(r"\s*&gt;\s+", " ", text)
    return text.strip()


def wrap_javadoc(text, indent="", first_prefix=""):
    """Wraps text into javadoc lines of roughly 100 characters."""
    words = text.split()
    lines = []
    current = first_prefix
    limit = 96 - len(indent)
    for word in words:
        if not current:
            candidate = word
        elif current.endswith(" "):
            candidate = current + word
        else:
            candidate = current + " " + word
        if current and len(candidate) > limit:
            lines.append(current)
            current = word
        else:
            current = candidate
    if current:
        lines.append(current)
    return lines


class Generator:
    def __init__(self, spec):
        self.spec = spec
        self.schemas = spec["components"]["schemas"]
        self.class_names = {}
        for name in self.schemas:
            self.class_names[name] = SCHEMA_NAME_OVERRIDES.get(name, pascal(name))
        duplicates = [n for n in self.class_names.values()
                      if list(self.class_names.values()).count(n) > 1]
        if duplicates:
            raise SystemExit("Class name conflict: %s" % sorted(set(duplicates)))
        self.files_written = []

    # --- schema helpers ----------------------------------------------------

    def ref_name(self, schema):
        ref = schema.get("$ref")
        return ref.split("/")[-1] if ref else None

    def resolve(self, schema):
        """Follows $ref until the actual schema is reached."""
        seen = 0
        while "$ref" in schema and seen < 10:
            schema = self.schemas[self.ref_name(schema)]
            seen += 1
        return schema

    def java_type(self, schema, field_name="", boxed=True):
        ref = self.ref_name(schema)
        if ref:
            return self.class_names[ref]
        kind = schema.get("type")
        fmt = schema.get("format")
        if kind == "string":
            if fmt == "date":
                return "java.time.LocalDate"
            if fmt == "date-time":
                return "java.time.OffsetDateTime"
            if fmt == "binary":
                return "byte[]"
            return "String"
        if kind == "number":
            return "java.math.BigDecimal"
        if kind == "integer":
            return "Long" if "amount" in field_name.lower() else "Integer"
        if kind == "boolean":
            return "Boolean"
        if kind == "array":
            item = schema.get("items", {})
            return "java.util.List<%s>" % self.java_type(item, field_name)
        if kind == "object" or "properties" in schema:
            return "java.util.Map<String, Object>"
        return "com.fasterxml.jackson.databind.JsonNode"

    def field_doc(self, schema):
        parts = []
        for key in ("title", "description"):
            value = schema.get(key)
            if value and value not in parts:
                parts.append(value.strip())
        text = ". ".join(parts)
        examples = schema.get("examples")
        if examples:
            sample = ", ".join(json.dumps(e, ensure_ascii=False) for e in examples[:2])
            text = (text + ". " if text else "") + "Example: " + sample
        enum_ref = self.ref_name(schema)
        return javadoc_escape(text)

    # --- model generation --------------------------------------------------

    def generate_models(self):
        for name, schema in sorted(self.schemas.items()):
            class_name = self.class_names[name]
            if "enum" in schema:
                source = self.render_enum(class_name, schema)
            else:
                source = self.render_record(class_name, schema)
            self.write(os.path.join(MODEL_DIR, class_name + ".java"), source)

    def render_enum(self, class_name, schema):
        title = javadoc_escape(schema.get("description") or schema.get("title") or class_name)
        lines = ["package %s;" % MODEL_PKG, "",
                 "import com.fasterxml.jackson.annotation.JsonCreator;",
                 "import com.fasterxml.jackson.annotation.JsonValue;", "", "/**"]
        for line in wrap_javadoc(title):
            lines.append(" * " + line)
        lines += [" *",
                  " * <p>A value that is not yet known to this version of the library is parsed",
                  " * as {@code null} instead of failing; use {@link #parse(String)} when an",
                  " * unknown value must be an error.",
                  " */",
                  "public enum %s {" % class_name, ""]
        values = schema["enum"]
        constants = []
        used = set()
        for value in values:
            constant = enum_constant(value)
            while constant in used:
                constant += "_X"
            used.add(constant)
            constants.append((constant, value))
        for index, (constant, value) in enumerate(constants):
            suffix = "," if index < len(constants) - 1 else ";"
            lines.append("    %s(%s)%s" % (constant, json.dumps(value, ensure_ascii=False), suffix))
        lines += ["",
                  "    private final String value;", "",
                  "    %s(String value) {" % class_name,
                  "        this.value = value;",
                  "    }", "",
                  "    /** The value as it is sent over the wire. */",
                  "    @JsonValue",
                  "    public String value() {",
                  "        return this.value;",
                  "    }", "",
                  "    /** Parses a wire value; an unknown one yields {@code null}. */",
                  "    @JsonCreator",
                  "    public static %s fromValue(String value) {" % class_name,
                  "        if (value == null) {",
                  "            return null;",
                  "        }",
                  "        for (%s candidate : values()) {" % class_name,
                  "            if (candidate.value.equals(value)) {",
                  "                return candidate;",
                  "            }",
                  "        }",
                  "        return null;",
                  "    }", "",
                  "    /** Parses a wire value, throwing on an unknown one. */",
                  "    public static %s parse(String value) {" % class_name,
                  "        %s parsed = fromValue(value);" % class_name,
                  "        if (parsed == null) {",
                  "            throw new IllegalArgumentException(",
                  "                    \"Неизвестное значение %s: \" + value);" % class_name,
                  "        }",
                  "        return parsed;",
                  "    }", "",
                  "    @Override",
                  "    public String toString() {",
                  "        return this.value;",
                  "    }",
                  "}", ""]
        return "\n".join(lines)

    def render_record(self, class_name, schema):
        properties = schema.get("properties", {})
        required = set(schema.get("required", []))
        fields = []
        for json_name, prop in properties.items():
            field = camel(json_name)
            fields.append({
                "json": json_name,
                "name": field,
                "type": self.java_type(prop, json_name),
                "doc": self.field_doc(prop),
                "required": json_name in required,
            })

        title = schema.get("description") or schema.get("title") or class_name
        lines = ["package %s;" % MODEL_PKG, "",
                 "import com.fasterxml.jackson.annotation.JsonIgnoreProperties;",
                 "import com.fasterxml.jackson.annotation.JsonInclude;",
                 "import com.fasterxml.jackson.annotation.JsonProperty;", "", "/**"]
        for line in wrap_javadoc(javadoc_escape(title)):
            lines.append(" * " + line)
        if fields:
            lines.append(" *")
            for field in fields:
                doc = field["doc"] or field["json"]
                mark = "" if field["required"] else " (optional)"
                wrapped = wrap_javadoc(doc + mark, indent="    ")
                lines.append(" * @param %s %s" % (field["name"], wrapped[0] if wrapped else field["json"]))
                for extra in wrapped[1:]:
                    lines.append(" *        " + extra)
        lines += [" */",
                  "@JsonIgnoreProperties(ignoreUnknown = true)",
                  "@JsonInclude(JsonInclude.Include.NON_NULL)"]

        if not fields:
            lines += ["public record %s() {" % class_name, "}", ""]
            return "\n".join(lines)

        lines.append("public record %s(" % class_name)
        for index, field in enumerate(fields):
            suffix = "," if index < len(fields) - 1 else ") {"
            lines.append("        @JsonProperty(\"%s\") %s %s%s"
                         % (field["json"], field["type"], field["name"], suffix))
        lines.append("")
        lines += self.render_builder(class_name, fields)
        lines += ["}", ""]
        return "\n".join(lines)

    def render_builder(self, class_name, fields):
        lines = ["    /** Builder for {@link %s}. */" % class_name,
                 "    public static Builder builder() {",
                 "        return new Builder();",
                 "    }", "",
                 "    /** A builder pre-filled with the values of this object. */",
                 "    public Builder toBuilder() {",
                 "        return new Builder()"]
        for index, field in enumerate(fields):
            suffix = ";" if index == len(fields) - 1 else ""
            lines.append("                .%s(this.%s)%s" % (field["name"], field["name"], suffix))
        lines += ["    }", "",
                  "    /** Builder for {@link %s}. */" % class_name,
                  "    public static final class Builder {", ""]
        for field in fields:
            lines.append("        private %s %s;" % (field["type"], field["name"]))
        lines.append("")
        for field in fields:
            doc = field["doc"]
            if doc:
                for line in wrap_javadoc(doc.strip(), first_prefix="/** "):
                    lines.append("        " + line)
                lines[-1] = lines[-1] + " */"
            lines += ["        public Builder %s(%s %s) {" % (field["name"], field["type"], field["name"]),
                      "            this.%s = %s;" % (field["name"], field["name"]),
                      "            return this;",
                      "        }", ""]
        lines.append("        public %s build() {" % class_name)
        args = ", ".join("this." + f["name"] for f in fields)
        if len(args) <= 90:
            lines.append("            return new %s(%s);" % (class_name, args))
        else:
            lines.append("            return new %s(" % class_name)
            for index, field in enumerate(fields):
                suffix = ");" if index == len(fields) - 1 else ","
                lines.append("                    this.%s%s" % (field["name"], suffix))
        lines += ["        }", "    }"]
        return lines

    def write(self, path, source):
        os.makedirs(os.path.dirname(path), exist_ok=True)
        with open(path, "w", encoding="utf-8") as handle:
            handle.write(source)
        self.files_written.append(path)

    # --- service class generation ------------------------------------------

    def response_plan(self, op):
        """Returns (kind, envelope_path, java_type, paginated) for the successful response."""
        content = op.get("responses", {}).get("200", {}).get("content", {})
        if "application/pdf" in content or "application/octet-stream" in content:
            return ("binary", [], "BinaryContent", False)
        if "application/json" not in content:
            return ("void", [], "void", False)

        schema = content["application/json"]["schema"]
        envelope = self.resolve(schema)
        path = []
        node = schema
        properties = envelope.get("properties", {})
        paginated = False
        if "Data" in properties:
            links = properties.get("Links", {})
            paginated = self.ref_name(links) == "PaginatedLinkModel"
            path.append("Data")
            node = properties["Data"]
        while True:
            resolved = self.resolve(node)
            inner = resolved.get("properties", {})
            required = set(resolved.get("required", []))
            if len(inner) == 1:
                only = next(iter(inner))
                if only in required:
                    path.append(only)
                    node = inner[only]
                    continue
            break
        return ("json", path, self.java_type(node), paginated)

    def request_plan(self, op):
        """Returns (envelope_path, java_type, parameter_name, doc) for the request body."""
        body = op.get("requestBody")
        if not body:
            return None
        schema = body["content"]["application/json"]["schema"]
        path = []
        node = schema
        name = None
        while True:
            resolved = self.resolve(node)
            inner = resolved.get("properties", {})
            required = set(resolved.get("required", []))
            if len(inner) == 1:
                only = next(iter(inner))
                if only in required:
                    path.append(only)
                    name = only
                    node = inner[only]
                    continue
            break
        java = self.java_type(node)
        param = camel(name) if name else "request"
        if param == "data":
            param = "request"
        return (path, java, param, self.field_doc(node) or "request body")

    def generate_apis(self):
        by_tag = OrderedDict((tag, []) for tag in TAG_TO_API)
        for path, operations in self.spec["paths"].items():
            for method, op in operations.items():
                if method not in ("get", "post", "put", "delete", "patch"):
                    continue
                tag = op["tags"][0]
                if tag not in by_tag:
                    raise SystemExit("Unknown OpenAPI tag, add it to TAG_TO_API: %s" % tag)
                by_tag[tag].append((method.upper(), path, op))

        for tag, operations in by_tag.items():
            class_name, summary = TAG_TO_API[tag]
            self.write(os.path.join(API_DIR, class_name + ".java"),
                       self.render_api(class_name, summary, operations))

    def render_api(self, class_name, summary, operations):
        imports = set()
        body = []
        option_classes = []
        for method, path, op in sorted(operations, key=lambda item: item[2]["summary"]):
            body.extend(self.render_operation(method, path, op, imports, option_classes))

        lines = ["package %s;" % API_PKG, "", "@@IMPORTS@@", "/**"]
        for line in wrap_javadoc(javadoc_escape(summary)):
            lines.append(" * " + line)
        lines += [" *",
                  " * <p>An instance is available from {@link com.tochka.api.TochkaClient}.",
                  " */",
                  "public final class %s {" % class_name, "",
                  "    private final Transport transport;", "",
                  "    public %s(Transport transport) {" % class_name,
                  "        this.transport = transport;",
                  "    }", ""]
        lines.extend(body)
        lines.append("}")
        lines.append("")
        source = "\n".join(lines)

        imports.add("com.tochka.api.http.Transport")
        import_block = "\n".join("import %s;" % name for name in sorted(imports))
        source = source.replace("@@IMPORTS@@", import_block + "\n")
        return source

    def render_operation(self, http_method, path, op, imports, option_classes):
        summary = op["summary"]
        name = camel(summary)
        path_params = sorted(
            (p for p in op.get("parameters", []) if p["in"] == "path"),
            key=lambda p: path.find("{" + p["name"] + "}"))
        query_params = [p for p in op.get("parameters", []) if p["in"] == "query"]
        header_params = [p for p in op.get("parameters", []) if p["in"] == "header"]
        required_query = [p for p in query_params if p.get("required")]
        optional_query = [p for p in query_params if not p.get("required")]

        kind, unwrap, return_type, paginated = self.response_plan(op)
        request = self.request_plan(op)

        signature = []
        statements = []
        docs = []

        for param in path_params:
            java_name = camel(param["name"])
            override = PATH_PARAM_OVERRIDES.get(param["name"])
            java_type = override[0] if override else "String"
            expression = override[1].format(name=java_name) if override else java_name
            signature.append((java_type, java_name))
            statements.append('                .path("%s", %s)' % (param["name"], expression))
            docs.append((java_name, javadoc_escape(param.get("description") or param["schema"].get("title", ""))))

        customer_code_query = None
        for param in required_query:
            java_name = camel(param["name"])
            java_type = self.java_type(param["schema"], param["name"])
            if param["name"] == "customerCode":
                customer_code_query = java_name
            signature.append((java_type, java_name))
            statements.append('                .query("%s", %s)' % (param["name"], java_name))
            docs.append((java_name, javadoc_escape(param.get("description") or param["schema"].get("title", ""))))

        if request:
            wrap_path, body_type, body_param, body_doc = request
            signature.append((body_type, body_param))
            docs.append((body_param, body_doc))

        for param in header_params:
            java_name = camel(param["name"])
            if param["name"] == "customer-code":
                statements.append('                .header("customer-code", %s != null ? %s : transport.defaultCustomerCode())'
                                  % (java_name, java_name))
                signature.append(("String", java_name))
                docs.append((java_name, "customer code; {@code null} takes the code configured on the client"))

        header_customer_code = None
        for param in header_params:
            if param["name"] == "customer-code":
                header_customer_code = camel(param["name"])

        options_class = None
        if optional_query:
            options_class = pascal(summary) + "Options"
            option_classes.append(options_class)

        lines = []
        lines.extend(self.render_method(
            name, summary, op, signature, docs, statements, http_method, path,
            request, kind, unwrap, return_type, imports, options_class=None, paginated=False))

        option_signature = None
        option_docs = None
        option_statements = None
        if options_class:
            lines.extend(self.render_options_class(options_class, optional_query, imports))
            option_signature = signature + [(options_class, "options")]
            option_docs = docs + [("options", "optional query parameters; {@code null} means defaults")]
            option_statements = list(statements)
            for param in optional_query:
                option_statements.append('                .query("%s", options == null ? null : options.%s())'
                                         % (param["name"], camel(param["name"])))
            lines.extend(self.render_method(
                name, summary, op, option_signature, option_docs, option_statements, http_method, path,
                request, kind, unwrap, return_type, imports, options_class=options_class, paginated=False))
            if paginated and return_type.startswith("java.util.List<"):
                lines.extend(self.render_method(
                    name + "Page", summary, op, option_signature, option_docs, option_statements,
                    http_method, path, request, kind, unwrap, return_type, imports,
                    options_class=options_class, paginated=True))
        elif paginated and return_type.startswith("java.util.List<"):
            lines.extend(self.render_method(
                name + "Page", summary, op, signature, docs, statements, http_method, path,
                request, kind, unwrap, return_type, imports, options_class=None, paginated=True))

        if header_customer_code:
            short_signature = [s for s in signature if s[1] != header_customer_code]
            short_docs = [d for d in docs if d[0] != header_customer_code]
            short_statements = [s.replace(
                '%s != null ? %s : transport.defaultCustomerCode()' % (header_customer_code, header_customer_code),
                'transport.defaultCustomerCode()') for s in statements]
            lines.extend(self.render_method(
                name, summary, op, short_signature, short_docs, short_statements, http_method, path,
                request, kind, unwrap, return_type, imports, options_class=None, paginated=False,
                default_customer_code=True))

        if customer_code_query:
            def drop_customer_code(sig, doc_list, stmts):
                return (
                    [s for s in sig if s[1] != customer_code_query],
                    [d for d in doc_list if d[0] != customer_code_query],
                    [s.replace('.query("customerCode", customerCode)',
                               '.query("customerCode", requireCustomerCode())') for s in stmts],
                )

            short_signature, short_docs, short_statements = drop_customer_code(signature, docs, statements)
            lines.extend(self.render_method(
                name, summary, op, short_signature, short_docs, short_statements, http_method, path,
                request, kind, unwrap, return_type, imports, options_class=None, paginated=False,
                default_customer_code=True))
            if option_signature:
                opt_sig, opt_docs, opt_stmts = drop_customer_code(
                    option_signature, option_docs, option_statements)
                lines.extend(self.render_method(
                    name, summary, op, opt_sig, opt_docs, opt_stmts, http_method, path,
                    request, kind, unwrap, return_type, imports, options_class=options_class,
                    paginated=False, default_customer_code=True))
                if paginated and return_type.startswith("java.util.List<"):
                    lines.extend(self.render_method(
                        name + "Page", summary, op, opt_sig, opt_docs, opt_stmts, http_method, path,
                        request, kind, unwrap, return_type, imports, options_class=options_class,
                        paginated=True, default_customer_code=True))

        return lines

    def render_method(self, name, summary, op, signature, docs, statements, http_method, path,
                      request, kind, unwrap, return_type, imports, options_class, paginated,
                      default_customer_code=False):
        if paginated:
            item_type = return_type[len("java.util.List<"):-1]
            java_return = "Page<%s>" % item_type
            imports.add("com.tochka.api.http.Page")
        elif kind == "binary":
            java_return = "BinaryContent"
            imports.add("com.tochka.api.http.BinaryContent")
        elif kind == "void":
            java_return = "void"
        else:
            java_return = return_type

        self.register_imports(java_return, imports)
        for java_type, _ in signature:
            self.register_imports(java_type, imports)

        lines = ["    /**"]
        description = op.get("description") or ""
        doc_text = javadoc_escape(summary + (". " + description if description else ""))
        for line in wrap_javadoc(doc_text):
            lines.append("     * " + line)
        if default_customer_code:
            lines += ["     *",
                      "     * <p>The customer code is taken from the client configuration"
                      " ({@code TochkaClient.builder().customerCode(...)})."]
        scopes = []
        for entry in op.get("security", []):
            for values in entry.values():
                scopes.extend(values)
        if scopes:
            lines += ["     *",
                      "     * <p>Required permissions: {@code %s}." % ", ".join(scopes)]
        if docs:
            lines.append("     *")
            for param_name, text in docs:
                wrapped = wrap_javadoc(text or param_name)
                lines.append("     * @param %s %s" % (param_name, wrapped[0] if wrapped else param_name))
                for extra in wrapped[1:]:
                    lines.append("     *        " + extra)
        lines += ["     */"]

        args = ", ".join("%s %s" % (self.simple(t), n) for t, n in signature)
        lines.append("    public %s %s(%s) {" % (self.simple(java_return), name, args))
        if default_customer_code:
            pass
        prefix = "        " if java_return == "void" else "        return "
        lines.append('%stransport.request("%s", "%s")' % (prefix, http_method, path))
        lines.extend(statements)
        if request:
            wrap_path, body_type, body_param, _ = request
            imports.add("com.tochka.api.http.Envelope")
            wrap_args = ", ".join('"%s"' % segment for segment in wrap_path)
            if wrap_args:
                lines.append("                .body(Envelope.wrap(%s, %s))" % (body_param, wrap_args))
            else:
                lines.append("                .body(%s)" % body_param)
        if kind == "binary":
            lines.append("                .asBinary();")
        elif kind == "void":
            lines.append("                .execute();")
        else:
            if unwrap:
                lines.append("                .unwrap(%s)" % ", ".join('"%s"' % s for s in unwrap))
            if paginated:
                item_type = self.simple(return_type[len("java.util.List<"):-1])
                imports.add("com.fasterxml.jackson.core.type.TypeReference")
                lines.append("                .asPage(new TypeReference<List<%s>>() {});" % item_type)
            elif return_type.startswith("java.util.List<") or return_type.startswith("java.util.Map<"):
                imports.add("com.fasterxml.jackson.core.type.TypeReference")
                lines.append("                .as(new TypeReference<%s>() {});" % self.simple(return_type))
            else:
                boxed = self.simple(return_type)
                lines.append("                .as(%s.class);" % boxed)
        lines.append("    }")
        lines.append("")
        return lines

    def render_options_class(self, class_name, params, imports):
        fields = []
        for param in params:
            fields.append({
                "json": param["name"],
                "name": camel(param["name"]),
                "type": self.java_type(param["schema"], param["name"]),
                "doc": javadoc_escape(param.get("description") or param["schema"].get("title", "")),
            })
        for field in fields:
            self.register_imports(field["type"], imports)

        lines = ["    /** Optional parameters of {@code %s}. */" % class_name.replace("Options", ""),
                 "    public record %s(" % class_name]
        for index, field in enumerate(fields):
            suffix = "," if index < len(fields) - 1 else ") {"
            lines.append("            %s %s%s" % (self.simple(field["type"]), field["name"], suffix))
        lines += ["",
                  "        public static Builder builder() {",
                  "            return new Builder();",
                  "        }", "",
                  "        /** Builder for {@link %s}. */" % class_name,
                  "        public static final class Builder {", ""]
        for field in fields:
            lines.append("            private %s %s;" % (self.simple(field["type"]), field["name"]))
        lines.append("")
        for field in fields:
            if field["doc"]:
                for line in wrap_javadoc(field["doc"].strip(), first_prefix="/** "):
                    lines.append("            " + line)
                lines[-1] += " */"
            lines += ["            public Builder %s(%s %s) {" % (field["name"], self.simple(field["type"]), field["name"]),
                      "                this.%s = %s;" % (field["name"], field["name"]),
                      "                return this;",
                      "            }", ""]
        lines += ["            public %s build() {" % class_name,
                  "                return new %s(%s);" % (class_name, ", ".join("this." + f["name"] for f in fields)),
                  "            }",
                  "        }",
                  "    }", ""]
        return lines

    # --- imports -----------------------------------------------------------

    def register_imports(self, java_type, imports):
        for match in re.findall(r"java\.[a-z.]+\.[A-Z][A-Za-z0-9_]*", java_type):
            imports.add(match)
        for match in re.findall(r"\b([A-Z][A-Za-z0-9_]*)\b", java_type):
            if match in self.class_names.values():
                imports.add(MODEL_PKG + "." + match)

    @staticmethod
    def simple(java_type):
        return re.sub(r"(java\.[a-z.]+\.)([A-Z])", r"\2", java_type)


JAVA_LANG_PATTERN = re.compile(r"\b(java\.(?:util|math|time)\.[A-Z][A-Za-z0-9_]*)\b")


def compact_imports(source):
    """Replaces fully qualified java.* names with simple ones and adds the imports."""
    lines = source.split("\n")
    found = set()
    for index, line in enumerate(lines):
        if line.startswith("import "):
            continue
        found.update(JAVA_LANG_PATTERN.findall(line))
        lines[index] = JAVA_LANG_PATTERN.sub(lambda m: m.group(1).rsplit(".", 1)[1], line)
    found = sorted(found)
    if not found:
        return source
    last_import = max((i for i, line in enumerate(lines) if line.startswith("import ")), default=None)
    existing = {line for line in lines if line.startswith("import ")}
    block = [line for line in ("import %s;" % name for name in found) if line not in existing]
    if not block:
        return "\n".join(lines)
    if last_import is None:
        insert_at = next(i for i, line in enumerate(lines) if line.startswith("package ")) + 1
        lines[insert_at:insert_at] = [""] + block
    else:
        lines[last_import + 1:last_import + 1] = block
    return "\n".join(lines)


REQUIRE_CUSTOMER_CODE = """
    private String requireCustomerCode() {
        String code = transport.defaultCustomerCode();
        if (code == null || code.isBlank()) {
            throw new IllegalStateException("Не задан customerCode: передайте его параметром метода "
                    + "или задайте по умолчанию через TochkaClient.builder().customerCode(...)");
        }
        return code;
    }
"""


def main():
    with open(SPEC, encoding="utf-8") as handle:
        spec = json.load(handle)

    generator = Generator(spec)
    for directory in (MODEL_DIR, API_DIR):
        if os.path.isdir(directory):
            shutil.rmtree(directory)
        os.makedirs(directory)

    generator.generate_models()
    models = len(generator.files_written)
    generator.generate_apis()

    for path in generator.files_written:
        with open(path, encoding="utf-8") as handle:
            source = handle.read()
        source = compact_imports(source)
        if path.startswith(API_DIR) and "requireCustomerCode()" in source:
            marker = "\n}\n"
            source = source[: source.rfind(marker)] + REQUIRE_CUSTOMER_CODE + marker
        with open(path, "w", encoding="utf-8") as handle:
            handle.write(source)

    version = spec["info"]["version"]
    print("OpenAPI %s: generated %d models and %d service classes"
          % (version, models, len(generator.files_written) - models))


if __name__ == "__main__":
    main()
