package com.payex.vas.demo.service.helper;

import com.payex.vas.demo.domain.entities.PaymentAccount;
import com.payex.vas.demo.domain.entities.PaymentOperation;
import com.payex.vas.demo.domain.payex.base.ResponseBase;
import com.payex.vas.demo.domain.payex.response.OperationResponse;
import com.payex.vas.demo.domain.payex.response.PaymentResponse;
import com.payex.vas.demo.domain.payex.response.TransactionResponse;
import com.payex.vas.demo.util.JsonUtil;

import java.time.OffsetDateTime;

public class PaymentOperationBuilder {

    public static PaymentOperation build(String operation, PaymentAccount paymentAccount, Long merchantId, PaymentResponse response) {
        return build(operation, paymentAccount, merchantId, response, response.getPaymentId());
    }

    public static PaymentOperation build(String operation, PaymentAccount paymentAccount, Long merchantId, TransactionResponse response) {

        return build(operation, paymentAccount, merchantId, response, response.getTransactionId());
    }

    public static PaymentOperation build(String operation, PaymentAccount paymentAccount, Long merchantId, OperationResponse response) {

        return build(operation, paymentAccount, merchantId, response, response.getOperationId());
    }

    private static PaymentOperation build(String operation, PaymentAccount paymentAccount, Long merchantId, ResponseBase response, String externalPaymentId) {

        var paymentOp = new PaymentOperation();
        paymentOp.setAmount(response.getAmount());
        paymentOp.setExternalPaymentId(externalPaymentId);
        paymentOp.setMerchantId(merchantId);
        paymentOp.setPaymentAccountId(paymentAccount.getId());
        paymentOp.setExternalAccountId(paymentAccount.getExternalAccountId());
        paymentOp.setPaymentOrderRef(response.getPaymentOrderRef());
        paymentOp.setPaymentTransactionRef(response.getPaymentTransactionRef());
        paymentOp.setState(response.getState());
        paymentOp.setTransactionType(operation);
        paymentOp.setJsonResponse(JsonUtil.mapToString(response));
        paymentOp.setCreated(OffsetDateTime.now());
        return paymentOp;
    }
}
