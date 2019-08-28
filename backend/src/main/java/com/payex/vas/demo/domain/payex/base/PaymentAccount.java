package com.payex.vas.demo.domain.payex.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaymentAccount {

    @JsonProperty("accountSummary")
    private AccountSummary accountSummary;

    @JsonProperty(value = "issuerSummary")
    private Issuer issuerSummary;
}
