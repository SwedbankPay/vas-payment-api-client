package com.payex.vas.demo.domain.payex.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Merchant {

    private String merchantName;
    private String terminalId;

    // For multipay merchants
    private String sellerId;
    private String sellerName;
}
