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

import static com.payex.vas.demo.util.Constants.*;
import static com.payex.vas.demo.util.Constants.ApiHeaders.AGREEMENT_MERCHANT_ID;

@Slf4j
@Component
@RequiredArgsConstructor
public class VasMultiPayApiRepository {



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

    private HttpEntity createPayload(Object payload, String agreementMerchantId) {
        var headers = new HttpHeaders();
        headers.add(AGREEMENT_MERCHANT_ID, agreementMerchantId);
        return new HttpEntity<>(payload, headers);
    }

    private String getOrderUrl(String postfix) {
        return applicationProperties.getVasMultiPayServerApi().getBaseOrderUrl() + postfix;
    }

    private String getOrderUrlWithParam(String postfix, Long orderId) {
        return applicationProperties.getVasMultiPayServerApi().getBaseOrderUrl() + String.format(postfix, orderId);
    }
}
