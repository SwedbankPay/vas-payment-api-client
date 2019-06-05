package com.payex.vas.demo.config.security;

import com.payex.vas.demo.util.error.InternalServerErrorException;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Slf4j
@Builder
public class HmacSignatureBuilder {
    public static final String HMAC_SHA_512 = "HmacSHA512";
    private static final byte DELIMITER = '\n';
    private String algorithm;
    private String method;
    private String resource;
    private String nonce;
    private String apiKey;
    private byte[] apiSecret;
    private byte[] payload;

    private byte[] buildSignature() {
        Objects.requireNonNull(algorithm, "algorithm");
        Objects.requireNonNull(method, "method");
        Objects.requireNonNull(resource, "resource");
        Objects.requireNonNull(apiKey, "apiKey");
        Objects.requireNonNull(payload, "payload");
        try {
            final var digest = Mac.getInstance(algorithm);
            var secretKey = new SecretKeySpec(apiSecret, algorithm);
            digest.init(secretKey);
            digest.update(method.getBytes(StandardCharsets.UTF_8));
            digest.update(DELIMITER);
            digest.update(resource.getBytes(StandardCharsets.UTF_8));
            digest.update(DELIMITER);
            digest.update(apiKey.getBytes(StandardCharsets.UTF_8));
            digest.update(DELIMITER);
            if (nonce != null) {
                digest.update(nonce.getBytes(StandardCharsets.UTF_8));
            }
            digest.update(DELIMITER);
            digest.update(payload);
            digest.update(DELIMITER);
            final var signatureBytes = digest.doFinal();
            digest.reset();
            return signatureBytes;
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("Unable to create HMAC signature", e);
            throw new InternalServerErrorException("Unable to create HMAC signature", e);
        }
    }

    private String buildAsBase64String() {
        return DatatypeConverter.printBase64Binary(buildSignature());
    }

    public String buildHmacHeaderValue() {
        return algorithm +
            " " +
            apiKey +
            ":" +
            nonce +
            ":" +
            buildAsBase64String();
    }

    @Override
    public String toString() {
        return "HmacSignatureBuilder{" +
            "algorithm='" + algorithm + '\'' +
            ", method='" + method + '\'' +
            ", resource='" + resource + '\'' +
            ", nonce='" + nonce + '\'' +
            ", apiKey='" + apiKey + '\'' +
            ", apiSecret=" + "*secret*" +
            ", payload=" + "*payload*" +
            '}';
    }
}
