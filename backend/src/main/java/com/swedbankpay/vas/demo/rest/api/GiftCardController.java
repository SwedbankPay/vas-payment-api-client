package com.swedbankpay.vas.demo.rest.api;

import com.swedbankpay.vas.demo.domain.swedbankpay.request.GiftCardDepositRequest;
import com.swedbankpay.vas.demo.domain.swedbankpay.request.GiftCardRequest;
import com.swedbankpay.vas.demo.domain.swedbankpay.response.GiftCardResponse;
import com.swedbankpay.vas.demo.domain.swedbankpay.response.PreDepositResponse;
import com.swedbankpay.vas.demo.service.GiftCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/gift-card")
public class GiftCardController {

    private final GiftCardService giftCardService;

    @PostMapping
    public ResponseEntity<GiftCardResponse> getGiftCard(@RequestBody GiftCardRequest request) {
        return ResponseEntity.ok(giftCardService.getGiftCard(request));
    }

    @PostMapping("/pre-deposit")
    public ResponseEntity<PreDepositResponse> preDeposit(@RequestBody GiftCardDepositRequest request){
        return ResponseEntity.ok(giftCardService.preDeposit(request));
    }
}
