package com.tochka.api.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

/**
 * Bank details of one side of a payment — the payer or the recipient.
 *
 * @param bankCode                  bank BIC
 * @param bankName                  bank name
 * @param bankCorrespondentAccount  correspondent account of the bank
 * @param account                   account number
 * @param name                      account holder name
 * @param amount                    amount
 * @param currency                  currency in ISO 4217 format
 * @param inn                       taxpayer number (ИНН)
 * @param kpp                       tax registration reason code (КПП)
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
