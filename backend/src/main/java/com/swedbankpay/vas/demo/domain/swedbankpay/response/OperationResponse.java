package com.swedbankpay.vas.demo.domain.swedbankpay.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swedbankpay.vas.demo.domain.swedbankpay.base.ResponseBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OperationResponse extends ResponseBase {

    @JsonProperty("operationId")
    private String operationId;

}
