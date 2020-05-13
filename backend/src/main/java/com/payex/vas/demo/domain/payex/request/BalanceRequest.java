package com.swedbankpay.vas.demo.domain.swedbankpay.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swedbankpay.vas.demo.domain.swedbankpay.base.AccountIdentifier;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BalanceRequest {

    @JsonProperty(value = "accountIdentifier")
    private AccountIdentifier accountIdentifier;
}
