package com.swedbankpay.vas.demo.domain.swedbankpay.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public abstract class AbstractRequest {

    @JsonIgnore
    private String operation;
    @JsonIgnore
    private String agreementMerchantId;
    @JsonIgnore
    private String traceId;
    @JsonIgnore
    private OffsetDateTime transmissionTime;
    @JsonIgnore
    private String hmac;
    @JsonIgnore
    private String paymentId;
    @JsonIgnore
    private String paymentAccountId;
}
