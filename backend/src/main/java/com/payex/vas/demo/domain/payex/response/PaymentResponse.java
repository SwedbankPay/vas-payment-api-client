package com.payex.vas.demo.domain.payex.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.payex.vas.demo.domain.payex.base.AccountIdentifier;
import com.payex.vas.demo.domain.payex.base.Issuer;
import com.payex.vas.demo.domain.payex.base.ResponseBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentResponse extends ResponseBase {

    @JsonProperty("paymentId")
    private String paymentId;

    @JsonProperty("remainingCaptureAmount")
    private Long remainingCaptureAmount;

    @JsonProperty("remainingReversalAmount")
    private Long remainingReversalAmount;

    @JsonProperty("remainingCancelAmount")
    private Long remainingCancelAmount;

    @JsonProperty("accountIdentifier")
    private AccountIdentifier accountIdentifier;

    @JsonProperty("issuer")
    private Issuer issuer;

    @JsonProperty("allowedProductIds")
    private List<String> allowedProductIds;
}
