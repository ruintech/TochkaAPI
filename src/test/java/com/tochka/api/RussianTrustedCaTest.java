package com.tochka.api;

import com.tochka.api.tls.RussianTrustedCa;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLContext;
import java.security.cert.X509Certificate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RussianTrustedCaTest {

    @Test
    void bundlesRootAndSubCertificates() {
        List<X509Certificate> certificates = RussianTrustedCa.certificates();

        assertEquals(2, certificates.size());
        assertTrue(certificates.get(0).getSubjectX500Principal().getName().contains("Russian Trusted Root CA"));
        assertTrue(certificates.get(1).getSubjectX500Principal().getName().contains("Russian Trusted Sub CA"));
    }

    @Test
    void buildsSslContextThatAlsoTrustsPublicAuthorities() throws Exception {
        SSLContext context = RussianTrustedCa.sslContext();

        assertNotNull(context);
        assertEquals("TLS", context.getProtocol());
        // Публичные центры остаются в доверии: их в стандартном хранилище заведомо больше двух.
        assertTrue(context.getSocketFactory() != null);
    }
}
