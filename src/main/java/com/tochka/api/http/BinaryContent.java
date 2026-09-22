package com.tochka.api.http;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

/**
 * Файл, полученный от API: счёт на оплату или закрывающий документ в PDF.
 *
 * @param bytes       содержимое файла
 * @param contentType MIME-тип из заголовка {@code Content-Type}
 * @param fileName    имя файла из {@code Content-Disposition}, если банк его прислал
 */
public record BinaryContent(byte[] bytes, String contentType, String fileName) {

    public BinaryContent {
        Objects.requireNonNull(bytes, "bytes");
    }

    public Optional<String> fileNameIfPresent() {
        return Optional.ofNullable(fileName);
    }

    public int size() {
        return bytes.length;
    }

    /**
     * Сохраняет файл на диск.
     *
     * @param target путь к файлу; существующий файл перезаписывается
     * @return тот же путь
     */
    public Path writeTo(Path target) {
        try {
            Files.write(target, bytes);
            return target;
        } catch (IOException e) {
            throw new UncheckedIOException("Не удалось сохранить файл в " + target, e);
        }
    }

    @Override
    public String toString() {
        return "BinaryContent[" + contentType + ", " + bytes.length + " bytes"
                + (fileName == null ? "" : ", " + fileName) + "]";
    }
}
