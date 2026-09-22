package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * ExternalConsentTypeEnum
 *
 * <p>A value that is not yet known to this version of the library is parsed
 * as {@code null} instead of failing; use {@link #parse(String)} when an
 * unknown value must be an error.
 */
public enum ExternalConsentTypeEnum {

    READ_ACCOUNTS_BASIC("ReadAccountsBasic"),
    READ_ACCOUNTS_DETAIL("ReadAccountsDetail"),
    READ_BALANCES("ReadBalances"),
    READ_STATEMENTS("ReadStatements"),
    READ_TRANSACTIONS_BASIC("ReadTransactionsBasic"),
    READ_TRANSACTIONS_CREDITS("ReadTransactionsCredits"),
    READ_TRANSACTIONS_DEBITS("ReadTransactionsDebits"),
    READ_TRANSACTIONS_DETAIL("ReadTransactionsDetail"),
    READ_CUSTOMER_DATA("ReadCustomerData"),
    READ_SBPDATA("ReadSBPData"),
    EDIT_SBPDATA("EditSBPData"),
    CREATE_PAYMENT_FOR_SIGN("CreatePaymentForSign"),
    CREATE_PAYMENT_ORDER("CreatePaymentOrder"),
    READ_ACQUIRING_DATA("ReadAcquiringData"),
    MAKE_ACQUIRING_OPERATION("MakeAcquiringOperation"),
    MANAGE_INVOICE_DATA("ManageInvoiceData"),
    MANAGE_WEBHOOK_DATA("ManageWebhookData"),
    MAKE_CUSTOMER("MakeCustomer"),
    MANAGE_GUARANTEE("ManageGuarantee"),
    MANAGE_EDO_DATA("ManageEdoData"),
    READ_BI_API("ReadBiApi"),
    READ_CUSTOMER_DATA_MCP("ReadCustomerDataMcp"),
    READ_ACCOUNTS_MCP("ReadAccountsMcp"),
    READ_BALANCES_MCP("ReadBalancesMcp"),
    READ_STATEMENTS_MCP("ReadStatementsMcp"),
    CREATE_PAYMENT_FOR_SIGN_MCP("CreatePaymentForSignMcp"),
    READ_FEEDBACK_DATA("ReadFeedbackData"),
    EDIT_FEEDBACK_DATA("EditFeedbackData");

    private final String value;

    ExternalConsentTypeEnum(String value) {
        this.value = value;
    }

    /** The value as it is sent over the wire. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Parses a wire value; an unknown one yields {@code null}. */
    @JsonCreator
    public static ExternalConsentTypeEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (ExternalConsentTypeEnum candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Parses a wire value, throwing on an unknown one. */
    public static ExternalConsentTypeEnum parse(String value) {
        ExternalConsentTypeEnum parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение ExternalConsentTypeEnum: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
