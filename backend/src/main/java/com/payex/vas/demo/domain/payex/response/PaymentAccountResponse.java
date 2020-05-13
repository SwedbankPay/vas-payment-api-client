package com.swedbankpay.vas.demo.domain.swedbankpay.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swedbankpay.vas.demo.domain.swedbankpay.base.PaymentAccount;
import lombok.Data;

@Data
public class PaymentAccountResponse {

    @JsonProperty("paymentAccount")
    private PaymentAccount paymentAccount;
}
