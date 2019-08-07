package com.payex.vas.demo.domain.payex.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ApiModel(value="PrivateCustomerIdentifier", description = "Either this or CorporateCustomerIdentifier must be present")
@AllArgsConstructor
@NoArgsConstructor //TODO:: Trenger ikke swagger anoteringer her
public class PrivateCustomerIdentifier extends MultiPayAccountIdentifier {


    @ApiModelProperty (
        name = "customerFirstName",
        notes = "given name of customer"
    )
    private String customerFirstName;

    @ApiModelProperty (
        name = "customerName",
        notes = "surname of customer"
    )
    private String customerLastName;

    @ApiModelProperty(
        name = "ssn",
        notes = "SSN of customer",
        allowEmptyValue = false
    )
    private String ssn;


    @Builder //TODO:: Ikke i bruk
    public PrivateCustomerIdentifier(String accountId, Address address, String contactEmail, String contactPhoneCountryCode, String languageCode, String contactPhone, String customerFirstName, String customerLastName, String ssn) {
        super(accountId, address, contactEmail, contactPhoneCountryCode, languageCode, contactPhone);
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.ssn = ssn;
    }
}
