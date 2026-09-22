/**
 * Java client for the open API of Tochka Bank.
 *
 * <p>{@link com.tochka.api.TochkaClient} is the entry point: it wires up the HTTP client and the
 * authorization, and exposes one service per API section ({@code accounts()},
 * {@code balances()}, {@code acquiring()}, {@code sbpQrCodes()} and the rest).
 *
 * <p>The other packages:
 * <ul>
 *   <li>{@code com.tochka.api.api} — API methods generated from the specification;</li>
 *   <li>{@code com.tochka.api.model} — request and response models;</li>
 *   <li>{@code com.tochka.api.auth} — JWT and OAuth 2.0 authorization;</li>
 *   <li>{@code com.tochka.api.webhook} — webhook signature verification and typed events;</li>
 *   <li>{@code com.tochka.api.tls} — Ministry of Digital Development certificates for TLS;</li>
 *   <li>{@code com.tochka.api.http} — transport: retries, logging, envelopes, files;</li>
 *   <li>{@code com.tochka.api.exception} — client exceptions.</li>
 * </ul>
 *
 * <p>Javadoc of the generated packages quotes the specification, so its text is in Russian.
 */
package com.tochka.api;
