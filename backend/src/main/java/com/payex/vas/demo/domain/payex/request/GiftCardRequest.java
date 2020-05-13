package com.swedbankpay.vas.demo.domain.swedbankpay.request;

import lombok.Data;

@Data
public class GiftCardRequest {
    private Long amount;
    private String email;
    private String msisdn;
    private String productId;
    private String agreementMerchantId;
}

