package com.swedbankpay.vas.demo.domain.swedbankpay.request;

import lombok.Data;

@Data
public class PingRequest {
    private String description;
    private String agreementMerchantId;
}
