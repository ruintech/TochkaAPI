package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * ExternalTransationTypeEnum
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
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

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
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

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
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
