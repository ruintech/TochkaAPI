package com.tochka.api.http;

import java.util.List;
import java.util.Optional;

/**
 * Страница списка вместе с блоками {@code Links} и {@code Meta} из ответа API.
 *
 * @param items      элементы текущей страницы
 * @param totalPages общее количество страниц
 * @param self       ссылка на текущую страницу
 * @param next       ссылка на следующую страницу, если она есть
 * @param prev       ссылка на предыдущую страницу, если она есть
 * @param first      ссылка на первую страницу
 * @param last       ссылка на последнюю страницу
 * @param <T>        тип элемента списка
 */
public record Page<T>(List<T> items,
                      Integer totalPages,
                      String self,
                      String next,
                      String prev,
                      String first,
                      String last) {

    public Page {
        items = items == null ? List.of() : List.copyOf(items);
    }

    /** Есть ли следующая страница. */
    public boolean hasNext() {
        return next != null && !next.isBlank();
    }

    public Optional<Integer> totalPagesIfPresent() {
        return Optional.ofNullable(totalPages);
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
