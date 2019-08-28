package com.payex.vas.demo.service.helper;

import com.payex.vas.demo.domain.dto.GenericPaymentRequest;
import com.payex.vas.demo.domain.entities.PaymentInstrument;
import com.payex.vas.demo.domain.entities.PaymentOperation;
import com.payex.vas.demo.domain.entities.SimulatedMerchant;
import com.payex.vas.demo.domain.payex.base.AccountIdentifier;
import com.payex.vas.demo.domain.payex.base.Merchant;
import com.payex.vas.demo.domain.payex.request.OperationRequest;
import com.payex.vas.demo.domain.payex.request.PaymentRequest;
import com.payex.vas.demo.domain.payex.request.TransactionRequest;

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

    public static OperationRequest buildOperationRequest(PaymentOperation orgRequest, SimulatedMerchant merchant) {
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
