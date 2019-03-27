package com.payex.vas.demo.domain.payex.request;

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
//    private List<Product> products; //TODO:: Do we need this?
}
