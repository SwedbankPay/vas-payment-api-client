package com.payex.vas.demo.rest.api;

import com.payex.vas.demo.domain.payex.request.GiftCardDepositRequest;
import com.payex.vas.demo.domain.payex.request.GiftCardRequest;
import com.payex.vas.demo.domain.payex.response.GiftCardResponse;
import com.payex.vas.demo.domain.payex.response.PreDepositResponse;
import com.payex.vas.demo.service.GiftCardService;
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

    @PostMapping("/get-new-gift-card")
    public ResponseEntity<GiftCardResponse> getGiftCard(@RequestBody GiftCardRequest request) {
        return ResponseEntity.ok(giftCardService.getGiftCard(request));
    }

    @PostMapping("/pre-deposit")
    public ResponseEntity<PreDepositResponse> preDeposit(@RequestBody GiftCardDepositRequest request){
        return ResponseEntity.ok(giftCardService.preDeposit(request));
    }
}
