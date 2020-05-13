package com.swedbankpay.vas.demo.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Builder
@Data
public class GenericPaymentResponse {

    private Long paymentId;
    private Long paymentAccountId;
    private Long merchantId;
    private String transactionType;
    private String state;
    private Long amount;
    private String externalPaymentId;
    private String externalAccountId;
    private String paymentOrderRef;
    private String paymentTransactionRef;
    private OffsetDateTime created;
    private String externalResponse;
}
