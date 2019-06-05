package com.payex.vas.demo.service.helper;

import com.payex.vas.demo.domain.entities.PaymentInstrument;
import com.payex.vas.demo.domain.entities.PaymentOperation;
import com.payex.vas.demo.domain.payex.base.ResponseBase;
import com.payex.vas.demo.domain.payex.response.OperationResponse;
import com.payex.vas.demo.domain.payex.response.PaymentResponse;
import com.payex.vas.demo.domain.payex.response.TransactionResponse;
import com.payex.vas.demo.util.JsonUtil;

import java.time.OffsetDateTime;

public class PaymentOperationBuilder {

    public static PaymentOperation build(String operation, PaymentInstrument paymentInstrument, Long merchantId, PaymentResponse response) {
        return build(operation, paymentInstrument, merchantId, response, response.getPaymentId());
    }

    public static PaymentOperation build(String operation, PaymentInstrument paymentInstrument, Long merchantId, TransactionResponse response) {

        return build(operation, paymentInstrument, merchantId, response, response.getTransactionId());
    }

    public static PaymentOperation build(String operation, PaymentInstrument paymentInstrument, Long merchantId, OperationResponse response) {

        return build(operation, paymentInstrument, merchantId, response, response.getOperationId());
    }

    private static PaymentOperation build(String operation, PaymentInstrument paymentInstrument, Long merchantId, ResponseBase response, String externalPaymentId) {

        var paymentOperation = new PaymentOperation();
        paymentOperation.setAmount(response.getAmount());
        paymentOperation.setExternalPaymentId(externalPaymentId);
        paymentOperation.setMerchantId(merchantId);
        paymentOperation.setPaymentInstrumentId(paymentInstrument.getId());
        paymentOperation.setExternalAccountId(paymentInstrument.getExternalAccountId());
        paymentOperation.setPaymentOrderRef(response.getPaymentOrderRef());
        paymentOperation.setPaymentTransactionRef(response.getPaymentTransactionRef());
        paymentOperation.setState(response.getState());
        paymentOperation.setTransactionType(operation);
        paymentOperation.setJsonResponse(JsonUtil.mapToString(response));
        paymentOperation.setCreated(OffsetDateTime.now());
        return paymentOperation;
    }
}
