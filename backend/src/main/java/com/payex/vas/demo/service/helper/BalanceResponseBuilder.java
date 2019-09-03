package com.payex.vas.demo.service.helper;

import com.payex.vas.demo.domain.dto.BalanceResponse;
import com.payex.vas.demo.domain.payex.response.PaymentAccountResponse;

public class BalanceResponseBuilder {

    public static BalanceResponse convertToBalanceResponse(PaymentAccountResponse paymentAccountResponse) {
        return BalanceResponse.builder()
            .balance(paymentAccountResponse.getPaymentAccount().getAccountSummary().getBalance())
            .expirationDate(paymentAccountResponse.getPaymentAccount().getAccountSummary().getExpiryDate())
            .accountId(paymentAccountResponse.getPaymentAccount().getAccountSummary().getAccountId())
            .build();
    }
}
