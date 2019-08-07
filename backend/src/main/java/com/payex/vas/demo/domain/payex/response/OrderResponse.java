package com.payex.vas.demo.domain.payex.response;

import com.payex.vas.demo.domain.payex.base.CorporateCustomerIdentifier;
import com.payex.vas.demo.domain.payex.base.Issuer;
import com.payex.vas.demo.domain.payex.base.PrivateCustomerIdentifier;
import lombok.Data;

@Data
public class OrderResponse {

    private String transactionId; //TODO:: Burden ikke denne hete orderId?
    private int amount; //TODO:: i request er denne Long..
    private CorporateCustomerIdentifier corporateCustomerIdentifier;
    private PrivateCustomerIdentifier privateCustomerIdentifier;
    private String created; //TODO:: Hva er denne? burde ikke det være en OffsetDateTime (om det er en dato)?
    private String currency;
    private String description;
    private String operation;
    private Issuer issuer;
    private String paymentId;
    private String paymentOrderRef;
    private String paymentTransactionRef;
    private int remainingCancelAmount; //TODO:: denne burde vel ikke være primitiv? Long?
    private int remainingCaptureAmount; //TODO:: denne burde vel ikke være primitiv? Long?
    private int remainingReversalAmount; //TODO:: denne burde vel ikke være primitiv? Long?
    private String state;
    private String transmissionTime; //TODO:: Hva er denne? burde ikke det være en OffsetDateTime (om det er en dato)?
    private String updated; //TODO:: Hva er denne? burde ikke det være en OffsetDateTime (om det er en dato)?

}
