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
 * Доверие к TLS-сертификатам Национального удостоверяющего центра Минцифры России, на которых
 * работает {@code enter.tochka.com}.
 *
 * <p>JVM использует собственное хранилище доверенных сертификатов и не читает системное, а
 * корневой и выпускающий сертификаты Минцифры не входят в стандартный {@code cacerts}. Без них
 * соединение обрывается с {@code PKIX path building failed} ещё до отправки запроса.
 *
 * <p>Оба сертификата (RSA-варианты, единственные, которые понимает JVM без ГОСТ-провайдера)
 * лежат в ресурсах библиотеки, поэтому обычно достаточно одной строки:
 *
 * <pre>{@code
 * TochkaClient client = TochkaClient.builder()
 *         .jwt(token)
 *         .sslContext(RussianTrustedCa.sslContext())
 *         .build();
 * }</pre>
 *
 * <p>Полученный контекст доверяет сертификатам Минцифры <em>в дополнение</em> к публичным
 * удостоверяющим центрам из {@code cacerts}, поэтому его безопасно использовать и для
 * остальных соединений приложения.
 *
 * <p>Проверить настройку можно обращением к тестовому домену банка
 * {@code https://tls-test.tochka.com/api} — он уже работает на сертификатах Минцифры и
 * отвечает {@code {"status":"ok"}}.
 */
public final class RussianTrustedCa {

    private static final String ROOT_RESOURCE = "/com/tochka/api/tls/russian_trusted_root_ca.pem";
    private static final String SUB_RESOURCE = "/com/tochka/api/tls/russian_trusted_sub_ca.pem";

    private static volatile SSLContext defaultContext;

    private RussianTrustedCa() {
    }

    /**
     * SSL-контекст, доверяющий сертификатам Минцифры и всем публичным центрам из стандартного
     * хранилища JVM. Собирается один раз и переиспользуется.
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
     * SSL-контекст со своими сертификатами вместо встроенных — например, если вы держите
     * актуальные копии в собственном каталоге.
     *
     * @param pemFiles PEM-файлы сертификатов (формат DER не поддерживается)
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

    /** SSL-контекст, доверяющий переданным сертификатам в дополнение к стандартному хранилищу. */
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

    /** Встроенные сертификаты: корневой Russian Trusted Root CA и выпускающий Russian Trusted Sub CA. */
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
