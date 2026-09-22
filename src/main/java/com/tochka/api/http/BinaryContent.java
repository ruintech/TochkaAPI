package com.tochka.api.http;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

/**
 * A file received from the API: an invoice or a closing document in PDF.
 *
 * @param bytes       file contents
 * @param contentType MIME type from the {@code Content-Type} header
 * @param fileName    file name from {@code Content-Disposition}, when the bank sends one
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
     * Writes the file to disk.
     *
     * @param target target path; an existing file is overwritten
     * @return the same path
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
