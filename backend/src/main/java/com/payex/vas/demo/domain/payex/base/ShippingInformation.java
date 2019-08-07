package com.payex.vas.demo.domain.payex.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor //TODO:: Trenger ikke swagger anoteringer her
public class ShippingInformation {

    @ApiModelProperty(
        name = "shippingAddressee"
    )
    private String shippingAddressee;

    @ApiModelProperty(
        name = "shippingStreetAddress"
    )
    private String shippingStreetAddress;

    @ApiModelProperty(
        name = "shippingCoAddress"
    )
    private String shippingCoAddress;

    @ApiModelProperty(
        name = "shippingPostalCode"
    )
    private String shippingPostalCode;

    @ApiModelProperty(
        name = "shippingCity"
    )
    private String shippingCity;

    @ApiModelProperty(
        name = "shippingCountryCode"
    )
    private String shippingCountryCode;

}
