package com.payex.vas.demo.domain.payex.request;

import com.payex.vas.demo.domain.payex.base.Merchant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OperationRequest {

    private String description;
    private Merchant merchant;

    private String paymentOrderRef;
    private String paymentTransactionRef;
}
