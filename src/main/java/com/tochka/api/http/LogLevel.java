package com.tochka.api.http;

/** How much of the HTTP exchange the client logs. */
public enum LogLevel {

    /** Log nothing. The default. */
    NONE,

    /** Method, URL, status and duration of the request. */
    BASIC,

    /** The same plus headers; {@code Authorization} is always masked. */
    HEADERS,

    /** The same plus request and response bodies. May contain personal and banking data. */
    BODY
}
