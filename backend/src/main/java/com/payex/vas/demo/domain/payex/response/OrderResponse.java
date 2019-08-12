package com.payex.vas.demo.domain.payex.response;

import com.payex.vas.demo.domain.entities.Product;
import com.payex.vas.demo.domain.payex.base.CorporateCustomerIdentifier;
import com.payex.vas.demo.domain.payex.base.Issuer;
import com.payex.vas.demo.domain.payex.base.PrivateCustomerIdentifier;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class OrderResponse {

    private String orderId;
    private Long amount;
    private CorporateCustomerIdentifier corporateCustomerIdentifier;
    private PrivateCustomerIdentifier privateCustomerIdentifier;
    private OffsetDateTime created;
    private String currency;
    private String description;
    private String operation;
    private Issuer issuer;
    private String paymentId;
    private String paymentOrderRef;
    private String paymentTransactionRef;
    private Long remainingCancelAmount;
    private Long remainingCaptureAmount;
    private Long remainingReversalAmount;
    private String state;
    private OffsetDateTime paymentTransmissionTime;
    private OffsetDateTime updated;
    private List<Product> products;
}
