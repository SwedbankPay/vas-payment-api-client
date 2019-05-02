package com.payex.vas.demo.config;

import com.google.common.base.Stopwatch;
import com.payex.vas.demo.config.security.HmacSignatureBuilder;
import com.payex.vas.demo.config.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.UUID;

import static com.payex.vas.demo.util.Constants.ApiHeaders.*;
import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@RequiredArgsConstructor
public class ExternalRequestInterceptor implements ClientHttpRequestInterceptor {


    private final ApplicationProperties applicationProperties;

    @Override
    @NonNull
    public ClientHttpResponse intercept(@NonNull HttpRequest request, @NonNull byte[] body, @NonNull ClientHttpRequestExecution execution) throws IOException {

        var stopwatch = Stopwatch.createStarted();
        var req = traceRequest(request, body);

        appendHeaders(request, body);

        var response = execution.execute(request, body);
        var resp = traceResponse(response, stopwatch);
        log.debug(req + resp + System.lineSeparator());
        return response;
    }

    private void appendHeaders(HttpRequest request, byte[] payload) {
        request.getHeaders().add(USERNAME, SecurityUtils.getCurrentUserLogin().orElse("anonymous"));
        request.getHeaders().add(TRANSMISSION_TIME, OffsetDateTime.now().toString());
        request.getHeaders().add(SESSION_ID, UUID.randomUUID().toString());
        request.getHeaders().add(HMAC, calculateHmac(request, payload));
    }

    private String calculateHmac(HttpRequest request, byte[] payload) {

        final var signatureBuilder = HmacSignatureBuilder.builder()
            .algorithm(HmacSignatureBuilder.HMAC_SHA_512)
            .method(request.getMethod() == null ? null : request.getMethod().name())
            .resource(request.getURI().getPath())
            .nonce(UUID.randomUUID().toString())
            .apiKey(applicationProperties.getVasPaymentServerApi().getApiKey())
            .apiSecret(applicationProperties.getVasPaymentServerApi().getApiSecret().getBytes(StandardCharsets.UTF_8))
            .payload(payload)
            .build();

        return signatureBuilder.buildHmacHeaderValue();
    }

    private String traceRequest(HttpRequest request, byte[] body) {
        var bodyText = (body != null && body.length > 0) ? ("Request body: " + new String(body, UTF_8) + System.lineSeparator()) : "";
        return System.lineSeparator() +
            "============================ Request begin ============================" + System.lineSeparator() +
            "URI         : " + request.getURI() + System.lineSeparator() +
            "Method      : " + request.getMethod() + System.lineSeparator() +
            "Headers     : " + createHttpHeaderStr(request.getHeaders()) + System.lineSeparator() +
            bodyText +
            "============================ Request end ============================";
    }

    private String traceResponse(ClientHttpResponse response, Stopwatch stopwatch) throws IOException {
        var body = StreamUtils.copyToString(response.getBody(), Charset.forName("ASCII"));
        var postFix = stopwatch == null ? "end" : "finished in " + stopwatch.stop();
        return System.lineSeparator() +
            "============================ Response begin ============================" + System.lineSeparator() +
            "Status code  : " + response.getStatusCode() + System.lineSeparator() +
            "Body         : " + body + System.lineSeparator() +
            "============================ Response " + postFix + " ============================";
    }

    private String createHttpHeaderStr(HttpHeaders httpHeaders) {
        var sb = new StringBuilder();
        sb.append("[");
        sb.append(System.lineSeparator());
        httpHeaders.forEach((key, value) -> sb.append("                 ")
            .append(key)
            .append(" : ")
            .append(value)
            .append(System.lineSeparator()));
        sb.append("              ]");
        return sb.toString();
    }
}
