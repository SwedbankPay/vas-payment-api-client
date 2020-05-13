package com.swedbankpay.vas.demo.service.helper;

import com.swedbankpay.vas.demo.domain.dto.GenericPaymentRequest;
import com.swedbankpay.vas.demo.domain.entities.PaymentInstrument;
import com.swedbankpay.vas.demo.domain.entities.PaymentOperation;
import com.swedbankpay.vas.demo.domain.entities.SimulatedMerchant;
import com.swedbankpay.vas.demo.domain.swedbankpay.base.AccountIdentifier;
import com.swedbankpay.vas.demo.domain.swedbankpay.base.Merchant;
import com.swedbankpay.vas.demo.domain.swedbankpay.request.OperationRequest;
import com.swedbankpay.vas.demo.domain.swedbankpay.request.PaymentRequest;
import com.swedbankpay.vas.demo.domain.swedbankpay.request.TransactionRequest;

import java.util.UUID;

public class ExternalRequestBuilder {

    public static TransactionRequest buildTransactionRequest(String operation, PaymentOperation orgRequest) {
        return TransactionRequest.builder()
            .amount(orgRequest.getAmount())
            .description(String.format("%s of %s with id: '%d'", operation, orgRequest.getTransactionType(), orgRequest.getId()))
            .paymentOrderRef(orgRequest.getPaymentOrderRef())
            .paymentTransactionRef(UUID.randomUUID().toString())
            .build();
    }

    public static OperationRequest buildCancelRequest(PaymentOperation orgRequest, SimulatedMerchant merchant) {
        return OperationRequest.builder()
            .description(String.format("Cancel of %s with id: '%d'", orgRequest.getTransactionType(), orgRequest.getId()))
            .paymentOrderRef(orgRequest.getPaymentOrderRef())
            .paymentTransactionRef(UUID.randomUUID().toString())
            .merchant(Merchant.builder()
                .merchantName(merchant.getMerchantName())
                .build())
            .build();
    }

    public static PaymentRequest buildPaymentRequest(GenericPaymentRequest request, PaymentInstrument paymentInstrument, SimulatedMerchant merchant) {
        return PaymentRequest.builder()
            .amount(request.getAmount())
            .description(request.getDescription())
            .currency(merchant.getCurrencyIso().getValue())
            .paymentOrderRef(UUID.randomUUID().toString())
            .paymentTransactionRef(UUID.randomUUID().toString())
            .merchant(Merchant.builder()
                .merchantName(merchant.getMerchantName())
                .build())
            .accountIdentifier(buildAccountIdentifier(paymentInstrument))
            .build();
    }

    public static AccountIdentifier buildAccountIdentifier(PaymentInstrument paymentInstrument) {
        return AccountIdentifier.builder()
            .accountId(paymentInstrument.getExternalAccountId())
            .accountKey(paymentInstrument.getPan())
            .cvc(paymentInstrument.getCvc())
            .build();
    }

}
