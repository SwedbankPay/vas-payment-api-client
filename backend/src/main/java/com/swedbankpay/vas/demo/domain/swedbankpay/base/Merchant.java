package com.swedbankpay.vas.demo.domain.swedbankpay.base;

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
}
