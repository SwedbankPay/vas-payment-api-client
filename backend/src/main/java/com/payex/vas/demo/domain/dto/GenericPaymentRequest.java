package com.payex.vas.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericPaymentRequest {
    private Long merchantId;

    private Long amount;
    private String description;
}
