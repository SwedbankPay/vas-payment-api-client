package com.payex.vas.demo.domain.payex.base;

import lombok.Data;

@Data
public class AccountSummaryGiftCard {
    private String expiryDate;
    private String accountKey;
    private String cvc;
    private Long balance;
}
