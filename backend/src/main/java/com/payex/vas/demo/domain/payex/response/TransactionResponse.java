package com.payex.vas.demo.domain.payex.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.payex.vas.demo.domain.payex.base.Issuer;
import com.payex.vas.demo.domain.payex.base.ResponseBase;
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
