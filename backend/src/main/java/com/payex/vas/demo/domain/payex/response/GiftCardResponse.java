package com.payex.vas.demo.domain.payex.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.payex.vas.demo.domain.payex.base.AccountSummaryGiftCard;
import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
public class GiftCardResponse extends AbstractLinkResponse {
    private String paymentId;

    @JsonProperty("accountSummaryGiftCard")
    private AccountSummaryGiftCard accountSummaryGiftCard;

}
