package com.payex.vas.demo.domain.payex.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ResponseBase {

    @JsonProperty("state")
    private String state;

    @JsonProperty("operation")
    private String operation;

    @JsonProperty("amount")
    private Long amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("description")
    private String description;

    @JsonProperty("paymentOrderRef")
    private String paymentOrderRef;

    @JsonProperty("paymentTransactionRef")
    private String paymentTransactionRef;

    @JsonProperty("merchant")
    private Merchant merchant;

    @JsonProperty("created")
    private OffsetDateTime created;

    @JsonProperty("updated")
    private OffsetDateTime updated;

    @JsonProperty("transmissionTime")
    private OffsetDateTime transmissionTime;
}
