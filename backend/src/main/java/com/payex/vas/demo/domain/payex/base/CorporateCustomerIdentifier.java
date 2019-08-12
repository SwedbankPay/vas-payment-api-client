package com.payex.vas.demo.domain.payex.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="CorporateCustomerIdentifier", description = "Either this or PrivateCustomerIdentifier must be present")
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor //TODO:: Trenger ikke swagger anoteringer her
public class CorporateCustomerIdentifier extends MultiPayAccountIdentifier {

    private String companyName;
    private String contactFirstName;
    private String contactLastName;
    private String vatRegistrationNumber;

}
