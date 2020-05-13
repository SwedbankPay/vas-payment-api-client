package com.swedbankpay.vas.demo.domain.entities;

import lombok.Data;

@Data
public class SimpleAccountIdentifier {
    private String accountKey;
    private String cvc;
    private String expiryDate;
}
