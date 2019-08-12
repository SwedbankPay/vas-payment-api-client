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
    protected String accountId;
    protected Address address;
    protected String contactEmail;
    protected String contactPhoneCountryCode;
    protected String languageCode;
    protected String contactPhone;
}
