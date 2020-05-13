package com.swedbankpay.vas.demo.domain.swedbankpay.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swedbankpay.vas.demo.domain.swedbankpay.base.AccountIdentifier;
import com.swedbankpay.vas.demo.domain.swedbankpay.base.Issuer;
import com.swedbankpay.vas.demo.domain.swedbankpay.base.ResponseBase;
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
