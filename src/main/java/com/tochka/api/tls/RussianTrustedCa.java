package com.tochka.api.tls;

import com.tochka.api.exception.TochkaException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * Trust for the TLS certificates of the Russian Ministry of Digital Development certification
 * authority, which {@code enter.tochka.com} runs on.
 *
 * <p>The JVM keeps its own trust store and never reads the system one, and neither the root nor
 * the issuing certificate of the ministry is part of the standard {@code cacerts}. Without them
 * the connection dies with {@code PKIX path building failed} before a request is even sent.
 *
 * <p>Both certificates (the RSA variants, the only ones the JVM understands without a GOST
 * provider) ship in the library resources, and the client uses them by default, so usually
 * nothing has to be configured. The context is also available on its own:
 *
 * <pre>{@code
 * SSLContext ssl = RussianTrustedCa.sslContext();
 * }</pre>
 *
 * <p>The resulting context trusts the ministry certificates <em>in addition</em> to the public
 * authorities from {@code cacerts}, so it is safe to use for the rest of the application too.
 *
 * <p>The setup can be checked against the bank test domain
 * {@code https://tls-test.tochka.com/api}: it already runs on the ministry certificates and
 * answers {@code {"status":"ok"}}.
 */
public final class RussianTrustedCa {

    private static final String ROOT_RESOURCE = "/com/tochka/api/tls/russian_trusted_root_ca.pem";
    private static final String SUB_RESOURCE = "/com/tochka/api/tls/russian_trusted_sub_ca.pem";

    private static volatile SSLContext defaultContext;

    private RussianTrustedCa() {
    }

    /**
     * An SSL context trusting the ministry certificates and every public authority from the
     * standard JVM trust store. Built once and reused.
     */
    public static SSLContext sslContext() {
        SSLContext context = defaultContext;
        if (context == null) {
            synchronized (RussianTrustedCa.class) {
                if (defaultContext == null) {
                    defaultContext = sslContext(certificates());
                }
                context = defaultContext;
            }
        }
        return context;
    }

    /**
     * An SSL context with your own certificates instead of the bundled ones — for example when
     * you keep up-to-date copies in your own directory.
     *
     * @param pemFiles certificate files in PEM format (DER is not supported)
     */
    public static SSLContext sslContextFrom(Path... pemFiles) {
        List<X509Certificate> certificates = new ArrayList<>();
        for (Path file : pemFiles) {
            try {
                certificates.addAll(parse(Files.readAllBytes(file)));
            } catch (IOException e) {
                throw new TochkaException("Не удалось прочитать сертификат " + file, e);
            }
        }
        return sslContext(certificates);
    }

    /** An SSL context trusting the given certificates in addition to the standard trust store. */
    public static SSLContext sslContext(List<X509Certificate> extraCertificates) {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);

            int index = 0;
            for (X509Certificate certificate : defaultTrustedCertificates()) {
                keyStore.setCertificateEntry("default-" + index++, certificate);
            }
            index = 0;
            for (X509Certificate certificate : extraCertificates) {
                keyStore.setCertificateEntry("extra-" + index++, certificate);
            }

            TrustManagerFactory factory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            factory.init(keyStore);

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, factory.getTrustManagers(), null);
            return context;
        } catch (GeneralSecurityException | IOException e) {
            throw new TochkaException("Не удалось собрать SSL-контекст с сертификатами Минцифры", e);
        }
    }

    /** The bundled certificates: Russian Trusted Root CA and Russian Trusted Sub CA. */
    public static List<X509Certificate> certificates() {
        List<X509Certificate> certificates = new ArrayList<>();
        certificates.addAll(readResource(ROOT_RESOURCE));
        certificates.addAll(readResource(SUB_RESOURCE));
        return List.copyOf(certificates);
    }

    private static List<X509Certificate> readResource(String resource) {
        try (InputStream stream = RussianTrustedCa.class.getResourceAsStream(resource)) {
            if (stream == null) {
                throw new TochkaException("Ресурс с сертификатом не найден: " + resource);
            }
            return parse(stream.readAllBytes());
        } catch (IOException e) {
            throw new TochkaException("Не удалось прочитать ресурс " + resource, e);
        }
    }

    @SuppressWarnings("unchecked")
    private static List<X509Certificate> parse(byte[] pem) {
        try {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            List<X509Certificate> result = new ArrayList<>();
            try (InputStream stream = new ByteArrayInputStream(pem)) {
                factory.generateCertificates(stream).forEach(certificate ->
                        result.add((X509Certificate) certificate));
            }
            if (result.isEmpty()) {
                throw new TochkaException("В файле нет сертификатов в формате PEM: "
                        + new String(pem, StandardCharsets.UTF_8).lines().findFirst().orElse(""));
            }
            return result;
        } catch (GeneralSecurityException | IOException e) {
            throw new TochkaException("Не удалось разобрать сертификат", e);
        }
    }

    private static List<X509Certificate> defaultTrustedCertificates() throws GeneralSecurityException {
        TrustManagerFactory factory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        factory.init((KeyStore) null);
        List<X509Certificate> result = new ArrayList<>();
        for (TrustManager manager : factory.getTrustManagers()) {
            if (manager instanceof X509TrustManager x509) {
                result.addAll(List.of(x509.getAcceptedIssuers()));
            }
        }
        return result;
    }
}
