package com.payex.vas.demo.domain.payex.base;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ApiModel(value="PrivateCustomerIdentifier", description = "Either this or CorporateCustomerIdentifier must be present")
@AllArgsConstructor
@NoArgsConstructor
public class PrivateCustomerIdentifier extends MultiPayAccountIdentifier {
    private String customerFirstName;
    private String customerLastName;
    private String ssn;

}
