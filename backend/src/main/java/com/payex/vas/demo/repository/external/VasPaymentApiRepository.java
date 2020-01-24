package com.payex.vas.demo.repository.external;

import com.google.common.base.Stopwatch;
import com.payex.vas.demo.config.ApplicationProperties;
import com.payex.vas.demo.domain.payex.request.*;
import com.payex.vas.demo.domain.payex.response.*;
import com.payex.vas.demo.util.JsonUtil;
import com.payex.vas.demo.util.error.BadRequestException;
import com.payex.vas.demo.util.error.InternalServerErrorException;
import com.payex.vas.demo.util.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import static com.payex.vas.demo.util.Constants.ApiHeaders.AGREEMENT_MERCHANT_ID;

@Slf4j
@Component
@RequiredArgsConstructor
public class VasPaymentApiRepository {

    private final static String PING_ENDPOINT = "/ping";
    private static final String GET_GIFTCARD_ENDPOINT = "/gift-card/get-new-gift-card";
    private static final String PRE_DEPOSIT_GIFTCARD_ENDPOINT = "/payments/gift-card/pre-deposit";
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

    private static final String BALANCE_URL = "/payment-account/balance";

    private final ApplicationProperties applicationProperties;
    private final RestTemplate restTemplate;


    public PaymentResponse purchase(PaymentRequest request, String externalAccountId, String agreementMerchantId) {
        var url = getUrlWithAccountAsParam(PAYMENT_PURCHASE_URL, externalAccountId);
        var payload = createPayload(request, agreementMerchantId);
        return executeForEntity(url, HttpMethod.POST, payload, PaymentResponse.class);
    }

    public PaymentResponse authorize(PaymentRequest request, String externalAccountId, String agreementMerchantId) {
        var url = getUrlWithAccountAsParam(PAYMENT_AUTH_URL, externalAccountId);
        var payload = createPayload(request, agreementMerchantId);
        return executeForEntity(url, HttpMethod.POST, payload, PaymentResponse.class);
    }

    public PaymentAccountResponse balance(BalanceRequest request, String agreementMerchantId) {
        var url = getUrl(BALANCE_URL);
        var payload = createPayload(request, agreementMerchantId);
        return executeForEntity(url, HttpMethod.POST, payload, PaymentAccountResponse.class);
    }

    public PaymentResponse deposit(PaymentRequest request, String externalAccountId, String agreementMerchantId) {
        var url = getUrlWithAccountAsParam(PAYMENT_DEPOSIT_URL, externalAccountId);
        var payload = createPayload(request, agreementMerchantId);
        return executeForEntity(url, HttpMethod.POST, payload, PaymentResponse.class);
    }

    public PaymentResponse credit(PaymentRequest request, String externalAccountId, String agreementMerchantId) {
        var url = getUrlWithAccountAsParam(PAYMENT_CREDIT_URL, externalAccountId);
        var payload = createPayload(request, agreementMerchantId);
        return executeForEntity(url, HttpMethod.POST, payload, PaymentResponse.class);
    }

    public OperationResponse cancel(OperationRequest request, String externalAccountId, String externalOrgPaymentId, String agreementMerchantId) {
        var url = getUrlWithAccountAndPaymentAsParam(PAYMENT_CANCEL_URL, externalAccountId, externalOrgPaymentId);
        var payload = createPayload(request, agreementMerchantId);
        return executeForEntity(url, HttpMethod.POST, payload, OperationResponse.class);
    }

    public TransactionResponse capture(TransactionRequest request, String externalAccountId, String externalOrgPaymentId, String agreementMerchantId) {
        var url = getUrlWithAccountAndPaymentAsParam(PAYMENT_CAPTURE_URL, externalAccountId, externalOrgPaymentId);
        var payload = createPayload(request, agreementMerchantId);
        return executeForEntity(url, HttpMethod.POST, payload, TransactionResponse.class);
    }

    public TransactionResponse reversal(TransactionRequest request, String externalAccountId, String externalOrgPaymentId, String agreementMerchantId) {
        var url = getUrlWithAccountAndPaymentAsParam(PAYMENT_REVERSAL_URL, externalAccountId, externalOrgPaymentId);
        var payload = createPayload(request, agreementMerchantId);
        return executeForEntity(url, HttpMethod.POST, payload, TransactionResponse.class);
    }

    public PaymentResponse getPayment(String externalAccountId, String externalPaymentId, String agreementMerchantId) {
        var url = getUrlWithAccountAndPaymentAsParam(GET_PAYMENT_URL, externalAccountId, externalPaymentId);
        var payload = createPayload(null, agreementMerchantId);
        return executeForEntity(url, HttpMethod.GET, payload, PaymentResponse.class);
    }

    public OperationResponse getOperation(String externalAccountId, String externalPaymentId, String agreementMerchantId) {
        var url = getUrlWithAccountAndPaymentAsParam(GET_OPERATION_URL, externalAccountId, externalPaymentId);
        var payload = createPayload(null, agreementMerchantId);
        return executeForEntity(url, HttpMethod.GET, payload, OperationResponse.class);
    }

    public TransactionResponse getTransaction(String externalAccountId, String externalPaymentId, String agreementMerchantId) {
        var url = getUrlWithAccountAndPaymentAsParam(GET_TRANSACTION_URL, externalAccountId, externalPaymentId);
        var payload = createPayload(null, agreementMerchantId);
        return executeForEntity(url, HttpMethod.GET, payload, TransactionResponse.class);
    }

    private HttpEntity createPayload(Object payload, String agreementMerchantId) {
        var headers = new HttpHeaders();
        headers.add(AGREEMENT_MERCHANT_ID, agreementMerchantId);
        return new HttpEntity<>(payload, headers);
    }

    public GiftCardResponse getGiftCard(GiftCardRequest giftCardRequest){
        var payload = createPayload(giftCardRequest, giftCardRequest.getAgreementMerchantId());
        return executeForEntity(getUrl(GET_GIFTCARD_ENDPOINT), HttpMethod.POST, payload, GiftCardResponse.class);
    }

    public PreDepositResponse preDeposit(GiftCardDepositRequest request) {
        var payload = createPayload(request, request.getAgreementMerchantId());
        return executeForEntity(getUrl(PRE_DEPOSIT_GIFTCARD_ENDPOINT), HttpMethod.POST, payload, PreDepositResponse.class);
    }
    public PingResponse pingPong(PingRequest request) {
        var payload = createPayload(request, request.getAgreementMerchantId());
        return executeForEntity(getUrl(PING_ENDPOINT), HttpMethod.POST, payload, PingResponse.class);
    }
    private String getUrlWithAccountAsParam(String postfix, String externalAccountId) {
        return applicationProperties.getVasPaymentServerApi().getBaseUrl() + String.format(postfix, externalAccountId);
    }

    private String getUrlWithAccountAndPaymentAsParam(String postfix, String externalAccountId, String externalPaymentId) {
        return applicationProperties.getVasPaymentServerApi().getBaseUrl() + String.format(postfix, externalAccountId, externalPaymentId);
    }

    private String getUrl(String postfix) {
        return applicationProperties.getVasPaymentServerApi().getBaseUrl() + postfix;
    }

    private <T> T executeForEntity(String url, HttpMethod httpMethod, HttpEntity payload, Class<T> entityClass) {
        var stopwatch = Stopwatch.createStarted();
        try {
            log.debug("About to make a {} REST call to {}", httpMethod, url);

            var response = restTemplate.exchange(url, httpMethod, payload, entityClass);
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
            var responseBody = ex.getResponseBodyAsString();
            log.warn("Failed while invoking '{}'. Got status: '{} - {}', with body: {}",
                url,
                ex.getStatusCode().value(),
                ex.getStatusCode().getReasonPhrase(),
                responseBody);
            var backendMsg = JsonUtil.getValueFromJsonString("message", responseBody);
            if (StringUtils.isEmpty(backendMsg))
                backendMsg = JsonUtil.getValueFromJsonString("title", responseBody);

            var exceptionMsg = !StringUtils.isEmpty(backendMsg) ? backendMsg : responseBody;
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
