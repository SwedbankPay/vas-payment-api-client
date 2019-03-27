package com.payex.vas.demo.repository.external;

import com.google.common.base.Stopwatch;
import com.payex.vas.demo.config.ApplicationProperties;
import com.payex.vas.demo.config.security.SecurityUtils;
import com.payex.vas.demo.domain.payex.request.OperationRequest;
import com.payex.vas.demo.domain.payex.request.PaymentRequest;
import com.payex.vas.demo.domain.payex.request.TransactionRequest;
import com.payex.vas.demo.domain.payex.response.OperationResponse;
import com.payex.vas.demo.domain.payex.response.PaymentResponse;
import com.payex.vas.demo.domain.payex.response.TransactionResponse;
import com.payex.vas.demo.util.JsonUtil;
import com.payex.vas.demo.util.error.BadRequestException;
import com.payex.vas.demo.util.error.InternalServerErrorException;
import com.payex.vas.demo.util.error.NotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;
import java.util.UUID;

import static com.payex.vas.demo.util.Constants.ApiHeaders.*;

@Slf4j
@Component
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class VasPaymentApiRepository {

    private static final String PAYMENT_PURCHASE_URL = "/payment-account/%s/payment/purchase";
    private static final String PAYMENT_AUTH_URL = "/payment-account/%s/payment/authorize";
    private static final String PAYMENT_CREDIT_URL = "/payment-account/%s/payment/credit";
    private static final String PAYMENT_DEPOSIT_URL = "/payment-account/%s/payment/deposit";

    private static final String PAYMENT_CANCEL_URL = "/payment-account/%s/payment/%s/cancel";
    private static final String PAYMENT_CAPTURE_URL = "/payment-account/%s/payment/%s/capture";
    private static final String PAYMENT_REVERSAL_URL = "/payment-account/%s/payment/%s/reversal";

    private static final String GET_TRANSACTION_URL = "/payment-account/%s/payment/%s/transactions/%s";
    private static final String GET_OPERATION_URL = "/payment-account/%s/payment/%s/operations/%s";
    private static final String GET_PAYMENT_URL = "/payment-account/%s/payment/%s";

    private final ApplicationProperties applicationProperties;
    private final RestTemplate restTemplate;


    public PaymentResponse purchase(PaymentRequest request, String externalAccountId, String agreementMerchantId) {
        var url = getUrlWithAccountAsParam(PAYMENT_PURCHASE_URL, externalAccountId);
        var payload = createPayload(url, request, agreementMerchantId);
        return executeForEntity(url, HttpMethod.POST, payload, PaymentResponse.class);
    }

    public PaymentResponse authorize(PaymentRequest request, String externalAccountId, String agreementMerchantId) {
        var url = getUrlWithAccountAsParam(PAYMENT_AUTH_URL, externalAccountId);
        var payload = createPayload(url, request, agreementMerchantId);
        return executeForEntity(url, HttpMethod.POST, payload, PaymentResponse.class);
    }

    public PaymentResponse deposit(PaymentRequest request, String externalAccountId, String agreementMerchantId) {
        var url = getUrlWithAccountAsParam(PAYMENT_DEPOSIT_URL, externalAccountId);
        var payload = createPayload(url, request, agreementMerchantId);
        return executeForEntity(url, HttpMethod.POST, payload, PaymentResponse.class);
    }

    public PaymentResponse credit(PaymentRequest request, String externalAccountId, String agreementMerchantId) {
        var url = getUrlWithAccountAsParam(PAYMENT_CREDIT_URL, externalAccountId);
        var payload = createPayload(url, request, agreementMerchantId);
        return executeForEntity(url, HttpMethod.POST, payload, PaymentResponse.class);
    }

    public OperationResponse cancel(OperationRequest request, String externalAccountId, String externalOrgPaymentId, String agreementMerchantId) {
        var url = getUrlWithAccountAndPaymentAsParam(PAYMENT_CANCEL_URL, externalAccountId, externalOrgPaymentId);
        var payload = createPayload(url, request, agreementMerchantId);
        return executeForEntity(url, HttpMethod.POST, payload, OperationResponse.class);
    }

    public TransactionResponse capture(TransactionRequest request, String externalAccountId, String externalOrgPaymentId, String agreementMerchantId) {
        var url = getUrlWithAccountAndPaymentAsParam(PAYMENT_CAPTURE_URL, externalAccountId, externalOrgPaymentId);
        var payload = createPayload(url, request, agreementMerchantId);
        return executeForEntity(url, HttpMethod.POST, payload, TransactionResponse.class);
    }

    public TransactionResponse reversal(TransactionRequest request, String externalAccountId, String externalOrgPaymentId, String agreementMerchantId) {
        var url = getUrlWithAccountAndPaymentAsParam(PAYMENT_REVERSAL_URL, externalAccountId, externalOrgPaymentId);
        var payload = createPayload(url, request, agreementMerchantId);
        return executeForEntity(url, HttpMethod.POST, payload, TransactionResponse.class);
    }

    public PaymentResponse getPayment(String externalAccountId, String externalPaymentId, String agreementMerchantId) {
        var url = getUrlWithAccountAndPaymentAsParam(GET_PAYMENT_URL, externalAccountId, externalPaymentId);
        var payload = createPayload(url, null, agreementMerchantId);
        return executeForEntity(url, HttpMethod.GET, payload, PaymentResponse.class);
    }

    //TODO:: Do we need these?

    public OperationResponse getOperation(String externalAccountId, String externalPaymentId, String agreementMerchantId) {
        var url = getUrlWithAccountAndPaymentAsParam(GET_OPERATION_URL, externalAccountId, externalPaymentId);
        var payload = createPayload(url, null, agreementMerchantId);
        return executeForEntity(url, HttpMethod.GET, payload, OperationResponse.class);
    }

    public TransactionResponse getTransaction(String externalAccountId, String externalPaymentId, String agreementMerchantId) {
        var url = getUrlWithAccountAndPaymentAsParam(GET_TRANSACTION_URL, externalAccountId, externalPaymentId);
        var payload = createPayload(url, null, agreementMerchantId);
        return executeForEntity(url, HttpMethod.GET, payload, TransactionResponse.class);
    }

    private String calculateHmac(String url, Object payload) {
        return null; //TODO:: implement HMAC
    }

    private HttpEntity createPayload(String url, Object payload, String agreementMerchantId) {
        var headers = new HttpHeaders();
        headers.add(AGREEMENT_MERCHANT_ID, agreementMerchantId);
        headers.add(USERNAME, SecurityUtils.getCurrentUserLogin().orElse("anonymous"));
        headers.add(TRANSMISSION_TIME, OffsetDateTime.now().toString());
        headers.add(HMAC, calculateHmac(url, payload));
        headers.add(SESSION_ID, UUID.randomUUID().toString());
        return new HttpEntity<>(payload, headers);
    }


    private String getUrlWithAccountAsParam(String postfix, String externalAccountId) {
        return applicationProperties.getVasPaymentServerApiBaseUrl() + String.format(postfix, externalAccountId);
    }

    private String getUrlWithAccountAndPaymentAsParam(String postfix, String externalAccountId, String externalPaymentId) {
        return applicationProperties.getVasPaymentServerApiBaseUrl() + String.format(postfix, externalAccountId, externalPaymentId);
    }

    private <T> T executeForEntity(String url, HttpMethod httpMethod, HttpEntity payload, Class<T> entityClass) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            log.debug("About to make a {} REST call to {}", httpMethod, url);

            ResponseEntity<T> response = restTemplate.exchange(url, httpMethod, payload, entityClass);
            return response.getBody();
        } catch (HttpStatusCodeException ex) {
            return handleHttpStatusCodeException(url, ex);
        } catch (Exception ex) {
            log.error("Got exception while calling '{}'", url, ex);
            throw new InternalServerErrorException("Failed to call API");
        } finally {
            log.debug("Finished {} for {} in {}.", httpMethod, entityClass.getSimpleName(), stopwatch.stop());
        }
    }

    private <T> T handleHttpStatusCodeException(String url, HttpStatusCodeException ex) {
        if (ex.getStatusCode().is4xxClientError()) { //Don't log stacktrace
            String responseBody = ex.getResponseBodyAsString();
            log.warn("Failed while invoking '{}'. Got status: '{} - {}', with body: {}",
                url,
                ex.getStatusCode().value(),
                ex.getStatusCode().getReasonPhrase(),
                responseBody);
            String backendMsg = JsonUtil.getValueFromJsonString("message", responseBody);
            if (StringUtils.isEmpty(backendMsg))
                backendMsg = JsonUtil.getValueFromJsonString("title", responseBody);

            String exceptionMsg = !StringUtils.isEmpty(backendMsg) ? backendMsg : responseBody;
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND)
                throw new NotFoundException(exceptionMsg);
            else
                throw new BadRequestException(exceptionMsg);
        } else {
            log.error("Got exception while calling '{}'", url, ex);
            throw new InternalServerErrorException(ex.getResponseBodyAsString(), ex);
        }
    }

}
