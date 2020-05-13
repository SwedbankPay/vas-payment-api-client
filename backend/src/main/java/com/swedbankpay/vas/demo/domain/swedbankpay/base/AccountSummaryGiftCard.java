package com.swedbankpay.vas.demo.domain.swedbankpay.base;

import lombok.Data;

@Data
public class AccountSummaryGiftCard {
    private String expiryDate;
    private String accountKey;
    private String cvc;
    private Long balance;
}
