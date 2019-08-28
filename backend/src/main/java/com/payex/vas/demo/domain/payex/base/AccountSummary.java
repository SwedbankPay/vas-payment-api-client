package com.payex.vas.demo.domain.payex.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.payex.vas.demo.domain.entities.enums.CurrencyIso;
import lombok.Data;

@Data
public class AccountSummary {

    @JsonProperty("accountId")
    private String accountId;

    @JsonProperty("balance")
    private Long balance;

    @JsonProperty("expiryDate")
    private String expiryDate;

    @JsonProperty("paymentInstrumentType")
    private String paymentInstrumentType;

    @JsonProperty("currency")
    private CurrencyIso currency;
}
