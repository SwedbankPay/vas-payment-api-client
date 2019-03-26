package com.payex.vas.demo.service;

import com.payex.vas.demo.config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;
import java.util.UUID;

import static com.payex.vas.demo.util.Constants.ApiHeaders.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final RestTemplate restTemplate;
    private final ApplicationProperties applicationProperties;

    public void getPaymentForAccount(String accountId, String paymentId) {
        var url = getUrl(String.format("/payment-account/%s/payment/%s", accountId, paymentId));
        var payload = createPayload(url, null);
        var payment = restTemplate.exchange(url, HttpMethod.GET, payload, String.class);

        log.info("Payment: {}", payment.getBody());
    }

    private HttpEntity createPayload(String url, Object payload) {
        var headers = new HttpHeaders();
        headers.add(AGREEMENT_MERCHANT_ID, applicationProperties.getDefaultAgreementMerchantId());
        headers.add(USERNAME, "test-user"); //TODO:: Add username
        headers.add(TRANSMISSION_TIME, OffsetDateTime.now().toString());
        headers.add(HMAC, calculateHmac(url, payload));
        headers.add(SESSION_ID, UUID.randomUUID().toString());
        return new HttpEntity(headers);
    }

    private String calculateHmac(String url, Object payload) {
        return null; //TODO:: implement HMAC
    }

    private String getUrl(String postfix) {
        return applicationProperties.getVasPaymentServerApiBaseUrl() + postfix;
    }
}
