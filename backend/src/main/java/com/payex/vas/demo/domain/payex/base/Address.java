package com.payex.vas.demo.domain.payex.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {

    @ApiModelProperty(
        name = "streetAddress",
        notes = "name of the street"
    )
    private String streetAddress;

    @ApiModelProperty(
        name = "coAddress",
        notes = "example: c/o PayEx."
    )
    private String coAddress;

    @ApiModelProperty(
        name = "city",
        notes = "city where the streetAddress is located, example: Oslo"
    )
    private String city;

    @ApiModelProperty(
        name = "postalCode",
        notes = "postalCode: 0177"
    )
    private String postalCode;

    @ApiModelProperty(
        name = "countryCode",
        notes = "countryCode: "
    )
    private String countryCode;

    @ApiModelProperty(
        name = "billingStreetAddressee",
        notes = ""
    )
    private String billingStreetAddressee;

    @ApiModelProperty(
        name = "",
        notes = ""
    )
    private String billingStreetAddress;

    @ApiModelProperty(
        name = "billingStreetAddress",
        notes = ""
    )
    private String billingCoAddress;

    @ApiModelProperty(
        name = "billingCoAddress",
        notes = ""
    )
    private String billingPostalCode;

    @ApiModelProperty(
        name = "billingCity",
        notes = ""
    )
    private String billingCity;

    @ApiModelProperty(
        name = "billingCountryCode",
        notes = ""
    )
    private String billingCountryCode;

}
