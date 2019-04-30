package com.payex.vas.demo.domain.payex.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.payex.vas.demo.domain.payex.base.AccountIdentifier;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BalanceRequest {

    @JsonProperty(value = "accountIdentifier")
    private AccountIdentifier accountIdentifier;
}
