package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * ConsentTypeEnum
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
 */
public enum ConsentTypeEnum {

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
    READ_SBPDATA1_C("ReadSBPData1C"),
    EDIT_SBPDATA1_C("EditSBPData1C"),
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

    ConsentTypeEnum(String value) {
        this.value = value;
    }

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
    @JsonCreator
    public static ConsentTypeEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (ConsentTypeEnum candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
    public static ConsentTypeEnum parse(String value) {
        ConsentTypeEnum parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение ConsentTypeEnum: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
