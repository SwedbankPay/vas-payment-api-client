package com.payex.vas.demo.repository.external;

import com.payex.vas.demo.config.ApplicationProperties;
import com.payex.vas.demo.domain.payex.request.*;
import com.payex.vas.demo.domain.payex.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.payex.vas.demo.util.Constants.ApiHeaders.AGREEMENT_MERCHANT_ID;

@Slf4j
@Component
@RequiredArgsConstructor
public class VasMultiPayApiRepository {

    private static final String MULTIPAY_PURCHASE_URL = "/purchase";
    private static final String MULTIPAY_AUTH_URL = "/authorize";
    private static final String MULTIPAY_CREDIT_URL = "/credit";
    private static final String MULTIPAY_DEPOSIT_URL = "/deposit";

    private static final String MULTIPAY_CANCEL_URL = "/%s/cancel";
    private static final String MULTIPAY_CAPTURE_URL = "/%s/capture";
    private static final String MULTIPAY_REVERSAL_URL = "/%s/reversal";

    private static final String GET_TRANSACTION_URL = "/%s/transactions/%s";
    private static final String GET_OPERATION_URL = "/operations/%s";
    private static final String GET_PAYMENT_BY_ID_URL = "/%s";

    private static final String BALANCE_URL = "/balance";

    private static final String CREATE_ORDER_URL = "/create";
    private static final String GET_ORDER_URL = "/%d";
    private static final String CANCEL_ORDER_URL = "/%d/cancel";

    private final ApplicationProperties applicationProperties;
    private final RestTemplate restTemplate;

    public OrderResponse createOrder(OrderRequest request, String agreementMerchantId) {
        var url = getOrderUrl(CREATE_ORDER_URL);
        var payload = createPayload(request, agreementMerchantId);
        return RestTemplateHelper.executeForEntity(restTemplate, url, HttpMethod.POST, payload, OrderResponse.class);
    }

    public OrderResponse getOrder(Long orderId, String agreementMerchantId) {
        var url = getOrderUrlWithParam(GET_ORDER_URL, orderId);
        var payload = createPayload(null, agreementMerchantId);
        return RestTemplateHelper.executeForEntity(restTemplate, url, HttpMethod.GET, payload, OrderResponse.class);
    }

    public OrderResponse cancelOrder(Long orderId, String agreementMerchantId) {
        var url = getOrderUrlWithParam(CANCEL_ORDER_URL, orderId);
        var payload = createPayload(null, agreementMerchantId);
        return RestTemplateHelper.executeForEntity(restTemplate, url, HttpMethod.POST, payload, OrderResponse.class);
    }

    public PaymentResponse purchase(PaymentRequest request, String externalAccountId, String agreementMerchantId) { // externalAccountId probably not necessary
        var url = getUrl(MULTIPAY_PURCHASE_URL);
        var payload = createPayload(request, agreementMerchantId);
        return RestTemplateHelper.executeForEntity(restTemplate, url, HttpMethod.POST, payload, PaymentResponse.class);
    }

    public PaymentResponse authorize(PaymentRequest request, String externalAccountId, String agreementMerchantId) {
        var url = getUrl(MULTIPAY_AUTH_URL);
        var payload = createPayload(request, agreementMerchantId);
        return RestTemplateHelper.executeForEntity(restTemplate, url, HttpMethod.POST, payload, PaymentResponse.class);
    }

    public PaymentAccountResponse balance(BalanceRequest request, String agreementMerchantId) {
        var url = getUrl(BALANCE_URL);
        var payload = createPayload(request, agreementMerchantId);
        return RestTemplateHelper.executeForEntity(restTemplate, url, HttpMethod.POST, payload, PaymentAccountResponse.class);
    }

    public PaymentResponse deposit(PaymentRequest request, String externalAccountId, String agreementMerchantId) {
        var url = getUrlWithAccountAsParam(MULTIPAY_DEPOSIT_URL, externalAccountId);
        var payload = createPayload(request, agreementMerchantId);
        return RestTemplateHelper.executeForEntity(restTemplate, url, HttpMethod.POST, payload, PaymentResponse.class);
    }

    public PaymentResponse credit(PaymentRequest request, String externalAccountId, String agreementMerchantId) {
        var url = getUrlWithAccountAsParam(MULTIPAY_CREDIT_URL, externalAccountId);
        var payload = createPayload(request, agreementMerchantId);
        return RestTemplateHelper.executeForEntity(restTemplate, url, HttpMethod.POST, payload, PaymentResponse.class);
    }

    public OperationResponse cancel(OperationRequest request, String externalAccountId, String externalOrgPaymentId, String agreementMerchantId) {
        var url = getUrlWithAccountAndPaymentAsParam(MULTIPAY_CANCEL_URL, externalAccountId, externalOrgPaymentId);
        var payload = createPayload(request, agreementMerchantId);
        return RestTemplateHelper.executeForEntity(restTemplate, url, HttpMethod.POST, payload, OperationResponse.class);
    }

    public TransactionResponse capture(TransactionRequest request, String externalAccountId, String externalOrgPaymentId, String agreementMerchantId) {
        var url = getUrlWithAccountAndPaymentAsParam(MULTIPAY_CAPTURE_URL, externalAccountId, externalOrgPaymentId);
        var payload = createPayload(request, agreementMerchantId);
        return RestTemplateHelper.executeForEntity(restTemplate, url, HttpMethod.POST, payload, TransactionResponse.class);
    }

    public TransactionResponse reversal(TransactionRequest request, String externalAccountId, String externalOrgPaymentId, String agreementMerchantId) {
        var url = getUrlWithAccountAndPaymentAsParam(MULTIPAY_REVERSAL_URL, externalAccountId, externalOrgPaymentId);
        var payload = createPayload(request, agreementMerchantId);
        return RestTemplateHelper.executeForEntity(restTemplate, url, HttpMethod.POST, payload, TransactionResponse.class);
    }

    public PaymentResponse getPayment(String externalAccountId, String externalPaymentId, String agreementMerchantId) {
        var url = getUrlWithAccountAndPaymentAsParam(GET_PAYMENT_BY_ID_URL, externalAccountId, externalPaymentId);
        var payload = createPayload(null, agreementMerchantId);
        return RestTemplateHelper.executeForEntity(restTemplate, url, HttpMethod.GET, payload, PaymentResponse.class);
    }

    public OperationResponse getOperation(String externalAccountId, String externalPaymentId, String agreementMerchantId) {
        var url = getUrlWithAccountAndPaymentAsParam(GET_OPERATION_URL, externalAccountId, externalPaymentId);
        var payload = createPayload(null, agreementMerchantId);
        return RestTemplateHelper.executeForEntity(restTemplate, url, HttpMethod.GET, payload, OperationResponse.class);
    }

    public TransactionResponse getTransaction(String externalAccountId, String externalPaymentId, String agreementMerchantId) {
        var url = getUrlWithAccountAndPaymentAsParam(GET_TRANSACTION_URL, externalAccountId, externalPaymentId);
        var payload = createPayload(null, agreementMerchantId);
        return RestTemplateHelper.executeForEntity(restTemplate, url, HttpMethod.GET, payload, TransactionResponse.class);
    }

    private HttpEntity createPayload(Object payload, String agreementMerchantId) {
        var headers = new HttpHeaders();
        headers.add(AGREEMENT_MERCHANT_ID, agreementMerchantId);
        return new HttpEntity<>(payload, headers);
    }


    private String getUrlWithAccountAsParam(String postfix, String externalAccountId) {
        return applicationProperties.getVasMultiPayServerApi().getBaseUrl() + String.format(postfix, externalAccountId);
    }

    private String getUrlWithAccountAndPaymentAsParam(String postfix, String externalAccountId, String externalPaymentId) {
        return applicationProperties.getVasMultiPayServerApi().getBaseUrl() + String.format(postfix, externalAccountId, externalPaymentId);
    }

    private String getUrl(String postfix) {
        return applicationProperties.getVasMultiPayServerApi().getBaseUrl() + postfix;
    }

    private String getOrderUrl(String postfix) {
        return applicationProperties.getVasMultiPayServerApi().getBaseOrderUrl() + postfix;
    }

    private String getOrderUrlWithParam(String postfix, Long orderId) {
        return applicationProperties.getVasMultiPayServerApi().getBaseOrderUrl() + String.format(postfix, orderId);
    }
}
