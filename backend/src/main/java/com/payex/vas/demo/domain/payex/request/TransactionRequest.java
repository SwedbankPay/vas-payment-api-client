package com.swedbankpay.vas.demo.domain.swedbankpay.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionRequest {

    private Long amount;
    private String description;
    private String paymentOrderRef;
    private String paymentTransactionRef;
    private Boolean cancelRemainingAuth;
}
