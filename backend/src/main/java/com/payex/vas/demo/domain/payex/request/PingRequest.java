package com.payex.vas.demo.domain.payex.request;

import lombok.Data;

@Data
public class PingRequest {
    private String description;
    private String agreementMerchantId;
}
