package com.payex.vas.demo.service;

import com.payex.vas.demo.domain.payex.request.GiftCardDepositRequest;
import com.payex.vas.demo.domain.payex.request.GiftCardRequest;
import com.payex.vas.demo.domain.payex.response.GiftCardResponse;
import com.payex.vas.demo.domain.payex.response.PreDepositResponse;
import com.payex.vas.demo.repository.external.VasPaymentApiRepository;
import com.payex.vas.demo.util.error.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
@RequiredArgsConstructor
public class GiftCardService {
    private final VasPaymentApiRepository vasPaymentApiRepository;

    public GiftCardResponse getGiftCard(GiftCardRequest giftCardRequest){
        if(giftCardRequest.getAmount() != null) {
            giftCardRequest.setAmount(giftCardRequest.getAmount() * 100);
        }
        return vasPaymentApiRepository.getGiftCard(giftCardRequest);
    }

    public PreDepositResponse preDeposit(GiftCardDepositRequest request) {
        request.getSimpleAccountIdentifier().setExpiryDate(convertToShortDate(request.getSimpleAccountIdentifier().getExpiryDate()));
        if(request.getSimpleAccountIdentifier().getExpiryDate() == null){
            throw new BadRequestException("could not parse expiry date");
        }
        if(request.getAmount() != null) {
            request.setAmount(request.getAmount() * 100);
        }
        return vasPaymentApiRepository.preDeposit(request);
    }

    private String convertToShortDate(String expiryDate) {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter = new SimpleDateFormat("MM/yy");
        try {
            return formatter.format(parser.parse(expiryDate));
        }catch (Exception e){
            return null;
        }
    }

}
