package com.tochka.api.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

/**
 * Реквизиты стороны платежа — плательщика или получателя.
 *
 * @param bankCode                  БИК банка
 * @param bankName                  наименование банка
 * @param bankCorrespondentAccount  корреспондентский счёт банка
 * @param account                   номер счёта
 * @param name                      наименование владельца счёта
 * @param amount                    сумма
 * @param currency                  валюта в формате ISO 4217
 * @param inn                       ИНН
 * @param kpp                       КПП
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PaymentSide(
        String bankCode,
        String bankName,
        String bankCorrespondentAccount,
        String account,
        String name,
        BigDecimal amount,
        String currency,
        String inn,
        String kpp) {
}
