package com.tochka.api.http;

import java.util.List;
import java.util.Optional;

/**
 * A page of a list together with the {@code Links} and {@code Meta} blocks of the API response.
 *
 * @param items      items of the current page
 * @param totalPages total number of pages
 * @param self       link to the current page
 * @param next       link to the next page, when there is one
 * @param prev       link to the previous page, when there is one
 * @param first      link to the first page
 * @param last       link to the last page
 * @param <T>        list item type
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

    /** Whether a next page exists. */
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
