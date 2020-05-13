package com.swedbankpay.vas.demo.domain.swedbankpay.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreDepositResponse {
    private String paymentId;
    private Long balance;
}
