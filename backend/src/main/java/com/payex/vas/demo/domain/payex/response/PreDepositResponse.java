package com.payex.vas.demo.domain.payex.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreDepositResponse {
    private String paymentId;
    private Long balance;
}
