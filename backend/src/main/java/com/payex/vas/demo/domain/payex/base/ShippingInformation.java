package com.payex.vas.demo.domain.payex.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShippingInformation {
    private String shippingAddressee;
    private String shippingStreetAddress;
    private String shippingCoAddress;
    private String shippingPostalCode;
    private String shippingCity;
    private String shippingCountryCode;

}
