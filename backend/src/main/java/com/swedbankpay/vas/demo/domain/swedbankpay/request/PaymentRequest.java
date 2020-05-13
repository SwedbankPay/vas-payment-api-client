package com.swedbankpay.vas.demo.domain.swedbankpay.request;

import com.swedbankpay.vas.demo.domain.swedbankpay.base.AccountIdentifier;
import com.swedbankpay.vas.demo.domain.swedbankpay.base.Merchant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentRequest {

    private String currency;
    private Long amount;
    private String description;
    private String paymentOrderRef;
    private String paymentTransactionRef;
    private String stan;
    private Boolean repeat;
    private Merchant merchant;
    private AccountIdentifier accountIdentifier;
}
