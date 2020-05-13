package com.swedbankpay.vas.demo.domain.swedbankpay.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swedbankpay.vas.demo.domain.swedbankpay.base.Issuer;
import com.swedbankpay.vas.demo.domain.swedbankpay.base.ResponseBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TransactionResponse extends ResponseBase {

    @JsonProperty("transactionId")
    private String transactionId;

    @JsonProperty("remainingCaptureAmount")
    private Long remainingCaptureAmount;

    @JsonProperty("remainingReversalAmount")
    private Long remainingReversalAmount;

    @JsonProperty("issuer")
    private Issuer issuer;
}
