package com.payex.vas.demo.service;

import com.payex.vas.demo.domain.payex.request.PingRequest;
import com.payex.vas.demo.domain.payex.response.PingResponse;
import com.payex.vas.demo.rest.util.RestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PingService {
    private final static String PING_ENDPOINT = "/payments/ping";
    private final RestUtil restUtil;

    public PingResponse pingPong(PingRequest request){
        var payload = restUtil.createPayload(request, request.getAgreementMerchantId());
        return restUtil.executeForEntity(restUtil.getUrl(PING_ENDPOINT), HttpMethod.POST, payload, PingResponse.class);
    }
}
