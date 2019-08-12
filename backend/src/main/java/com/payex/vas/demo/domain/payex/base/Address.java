package com.payex.vas.demo.domain.payex.base;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {
    private String streetAddress;
    private String coAddress;
    private String city;
    private String postalCode;
    private String countryCode;
    private String billingStreetAddressee;
    private String billingStreetAddress;
    private String billingCoAddress;
    private String billingPostalCode;
    private String billingCity;
    private String billingCountryCode;

}
