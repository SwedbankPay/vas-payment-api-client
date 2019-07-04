package com.payex.vas.demo.domain.payex.response;

import com.payex.vas.demo.domain.payex.base.CorporateCustomerIdentifier;
import com.payex.vas.demo.domain.payex.base.Issuer;
import com.payex.vas.demo.domain.payex.base.PrivateCustomerIdentifier;
import lombok.Data;

@Data
public class OrderResponse {

    private String transactionId;
    private int amount;
    private CorporateCustomerIdentifier corporateCustomerIdentifier;
    private PrivateCustomerIdentifier privateCustomerIdentifier;
    private String created;
    private String currency;
    private String description;
    private String operation;
    private Issuer issuer;
    private String paymentId;
    private String paymentOrderRef;
    private String paymentTransactionRef;
    private int remainingCancelAmount;
    private int remainingCaptureAmount;
    private int remainingReversalAmount;
    private String state;
    private String transmissionTime;
    private String updated;

}
