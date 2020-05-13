package com.swedbankpay.vas.demo.service.helper;

import com.swedbankpay.vas.demo.domain.dto.BalanceResponse;
import com.swedbankpay.vas.demo.domain.swedbankpay.response.PaymentAccountResponse;

public class BalanceResponseBuilder {

    public static BalanceResponse convertToBalanceResponse(PaymentAccountResponse paymentAccountResponse) {
        return BalanceResponse.builder()
            .balance(paymentAccountResponse.getPaymentAccount().getAccountSummary().getBalance())
            .expirationDate(paymentAccountResponse.getPaymentAccount().getAccountSummary().getExpiryDate())
            .accountId(paymentAccountResponse.getPaymentAccount().getAccountSummary().getAccountId())
            .build();
    }
}
