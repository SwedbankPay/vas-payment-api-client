package com.payex.vas.demo.domain.payex.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="CorporateCustomerIdentifier", description = "Either this or PrivateCustomerIdentifier must be present")
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CorporateCustomerIdentifier extends MultiPayAccountIdentifier {

    @ApiModelProperty(
        name = "companyName",
        notes = "Name of the company"
    )
    private String companyName;

    @ApiModelProperty(
        name = "contactFirstName",
        notes = "contact persons given name",
        allowEmptyValue = false
    )
    private String contactFirstName;

    @ApiModelProperty(
        name = "contactLastName",
        notes = "contact persons surname",
        allowEmptyValue = false
    )
    private String contactLastName;

    @ApiModelProperty(
        name = "vatRegistrationNumber",
        notes = "Organization number of the company",
        allowEmptyValue = false
    )
    private String vatRegistrationNumber;

}
