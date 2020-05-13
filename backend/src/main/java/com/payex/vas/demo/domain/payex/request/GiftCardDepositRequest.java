package com.swedbankpay.vas.demo.domain.swedbankpay.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swedbankpay.vas.demo.domain.entities.SimpleAccountIdentifier;
import lombok.Data;

@Data
public class GiftCardDepositRequest {
    private String currency;
    private Long amount;
    private String description;
    private String orderRef;
    private String transactionRef;
    private String agreementMerchantId;

    @JsonProperty(value = "simpleAccountIdentifier", required = true)
    private SimpleAccountIdentifier simpleAccountIdentifier;
}
