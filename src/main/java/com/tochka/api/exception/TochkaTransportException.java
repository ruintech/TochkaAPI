package com.tochka.api.exception;

/**
 * Запрос не дошёл до API или ответ не удалось прочитать: сетевая ошибка, таймаут,
 * несостоявшееся TLS-рукопожатие, нечитаемое тело ответа.
 *
 * <p>Ошибка TLS при обращении к {@code enter.tochka.com} чаще всего означает, что в
 * доверенное хранилище JVM не добавлены сертификаты Минцифры — см.
 * {@link com.tochka.api.tls.RussianTrustedCa}.
 */
public class TochkaTransportException extends TochkaException {

    private static final long serialVersionUID = 1L;

    public TochkaTransportException(String message, Throwable cause) {
        super(message, cause);
    }
}
