package com.payex.vas.demo.domain.payex.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.payex.vas.demo.domain.payex.base.PaymentAccount;
import lombok.Data;

@Data
public class PaymentAccountResponse {

    @JsonProperty("paymentAccount")
    private PaymentAccount paymentAccount;
}
