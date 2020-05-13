package com.swedbankpay.vas.demo.domain.swedbankpay.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swedbankpay.vas.demo.domain.swedbankpay.base.AccountSummaryGiftCard;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class GiftCardResponse {
    private String paymentId;

    @JsonProperty("accountSummaryGiftCard")
    private AccountSummaryGiftCard accountSummaryGiftCard;

}
