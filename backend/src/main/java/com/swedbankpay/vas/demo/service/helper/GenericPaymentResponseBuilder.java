package com.swedbankpay.vas.demo.service.helper;

import com.swedbankpay.vas.demo.domain.dto.GenericPaymentResponse;
import com.swedbankpay.vas.demo.domain.entities.PaymentOperation;

public class GenericPaymentResponseBuilder {

    public static GenericPaymentResponse convertToGenericPaymentResponse(PaymentOperation paymentOperation) {
        return GenericPaymentResponse.builder()
            .paymentId(paymentOperation.getId())
            .paymentAccountId(paymentOperation.getPaymentInstrumentId())
            .amount(paymentOperation.getAmount())
            .externalPaymentId(paymentOperation.getExternalPaymentId())
            .externalAccountId(paymentOperation.getExternalAccountId())
            .merchantId(paymentOperation.getMerchantId())
            .paymentOrderRef(paymentOperation.getPaymentOrderRef())
            .paymentTransactionRef(paymentOperation.getPaymentTransactionRef())
            .transactionType(paymentOperation.getTransactionType())
            .state(paymentOperation.getState())
            .created(paymentOperation.getCreated())
            .externalResponse(paymentOperation.getJsonResponse())
            .build();
    }
}
