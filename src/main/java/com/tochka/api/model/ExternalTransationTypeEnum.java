package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * ExternalTransationTypeEnum
 *
 * <p>A value that is not yet known to this version of the library is parsed
 * as {@code null} instead of failing; use {@link #parse(String)} when an
 * unknown value must be an error.
 */
public enum ExternalTransationTypeEnum {

    NEOPREDELENNOE_ZNACHENIE("Неопределенное значение"),
    PLATEZHNOE_PORUCHENIE("Платежное поручение"),
    PLATEZHNOE_TREBOVANIE("Платежное требование"),
    DENEZHNYY_CHEK_RKO("Денежный чек, РКО"),
    OBYAVLENIE_NA_VZNOS_NALICHNYMI_PKO("Объявление на взнос наличными, ПКО"),
    TREBOVANIE_PORUCHENIE("Требование-поручение"),
    INKASSOVOE_PORUCHENIE("Инкассовое поручение"),
    RASCHETNYY_CHEK("Расчетный чек"),
    AKKREDITIV("Аккредитив"),
    MEMORIALNYY_ORDER("Мемориальный ордер"),
    POGASHENIE_KREDITA("Погашение кредита"),
    VYDACHA_KREDITA("Выдача кредита"),
    AVIZO("Авизо"),
    BANKOVSKIE_KARTY("Банковские карты"),
    PLATEZHNYY_ORDER("Платежный ордер"),
    BANKOVSKIY_ORDER("Банковский ордер"),
    ORDER_PO_PEREDACHE_TSENNOSTEY("Ордер по передаче ценностей"),
    PROGRAMMNYY_ORDER("Программный ордер"),
    IMPORTIROVANNAYA_ZAPIS("Импортированная запись");

    private final String value;

    ExternalTransationTypeEnum(String value) {
        this.value = value;
    }

    /** The value as it is sent over the wire. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Parses a wire value; an unknown one yields {@code null}. */
    @JsonCreator
    public static ExternalTransationTypeEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (ExternalTransationTypeEnum candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Parses a wire value, throwing on an unknown one. */
    public static ExternalTransationTypeEnum parse(String value) {
        ExternalTransationTypeEnum parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение ExternalTransationTypeEnum: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
