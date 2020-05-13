package com.swedbankpay.vas.demo.domain.swedbankpay.request;

import com.swedbankpay.vas.demo.domain.swedbankpay.base.Merchant;
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
