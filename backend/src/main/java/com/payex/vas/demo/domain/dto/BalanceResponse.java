package com.payex.vas.demo.domain.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BalanceResponse {

    private String accountId;
    private Long balance;
    private String expirationDate;
}
