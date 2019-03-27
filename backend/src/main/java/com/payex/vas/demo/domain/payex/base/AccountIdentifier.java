package com.payex.vas.demo.domain.payex.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountIdentifier {

    private String accountKey;
    private String accountId;
    private String cvc;
    private String securityCode;
    private String expiryDate;
    private String track2;
}
