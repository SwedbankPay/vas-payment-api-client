package com.payex.vas.demo.domain.payex.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public abstract class AbstractRequest {

    @JsonIgnore
    private String operation;
    @JsonIgnore
    private String agreementMerchantId;
    @JsonIgnore
    private String traceId;
    @JsonIgnore
    private ZonedDateTime transmissionTime;
    @JsonIgnore
    private String hmac;
    @JsonIgnore
    private String paymentId;
    @JsonIgnore
    private String orderId;
}
