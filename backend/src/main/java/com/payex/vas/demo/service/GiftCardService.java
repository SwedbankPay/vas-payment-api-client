package com.payex.vas.demo.service;

import com.payex.vas.demo.domain.payex.request.GiftCardDepositRequest;
import com.payex.vas.demo.domain.payex.request.GiftCardRequest;
import com.payex.vas.demo.domain.payex.response.GiftCardResponse;
import com.payex.vas.demo.domain.payex.response.PreDepositResponse;
import com.payex.vas.demo.repository.external.VasPaymentApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GiftCardService {
    private final VasPaymentApiRepository vasPaymentApiRepository;

    public GiftCardResponse getGiftCard(GiftCardRequest giftCardRequest){
        return vasPaymentApiRepository.getGiftCard(giftCardRequest);
    }

    public PreDepositResponse preDeposit(GiftCardDepositRequest request) {
        return vasPaymentApiRepository.preDeposit(request);
    }

}
