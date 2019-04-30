package com.payex.vas.demo.domain.payex.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccountSummary {

    @JsonProperty("balance")
    private Long balance;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("state")
    private String state;

    @JsonProperty("expiryDate")
    private String expireDate;

    @JsonProperty("paymentInstrument")
    private String paymentInstrument;
}
