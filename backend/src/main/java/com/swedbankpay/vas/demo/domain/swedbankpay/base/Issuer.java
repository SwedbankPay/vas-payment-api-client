package com.swedbankpay.vas.demo.domain.swedbankpay.base;

import lombok.Data;

@Data
public class Issuer {

    private String issuerId;
    private String issuerName;
    private String acquirerId;
    private String acquirerName;
    private boolean settlementProvided;
}
