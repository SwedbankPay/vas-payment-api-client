package com.payex.vas.demo.domain.payex.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultiPayAccountIdentifier {

    @ApiModelProperty(
        name = "accountId",
        notes = "PayEx internal id for card/account",
        allowEmptyValue = true)
    protected String accountId;

    @JsonProperty(value = "address")
    protected Address address;

    @ApiModelProperty(
        name = "contactEmail",
        notes = "Email address, need either email, phone, or address",
        allowEmptyValue = true // may be changed to  false later
    )
    protected String contactEmail;

    @ApiModelProperty(
        name = "contactPhoneCountryCode",
        notes = "ISO 3166-1 alpha -2",
        allowEmptyValue = true
    )
    protected String contactPhoneCountryCode;

    @ApiModelProperty(
        name = "languageCode",
        notes = "a person can live in another country but choose their own languageCode",
        allowEmptyValue = true
    )
    protected String languageCode;

    @ApiModelProperty(
        name = "contactPhone",
        notes = "Phone number, need either email, phone, or address",
        allowEmptyValue = true // may be changed to false later
    )
    protected String contactPhone;
}
