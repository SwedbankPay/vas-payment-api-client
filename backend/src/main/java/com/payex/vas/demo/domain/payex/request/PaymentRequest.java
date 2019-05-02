package com.payex.vas.demo.domain.payex.request;

import com.payex.vas.demo.domain.payex.base.AccountIdentifier;
import com.payex.vas.demo.domain.payex.base.Merchant;
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
