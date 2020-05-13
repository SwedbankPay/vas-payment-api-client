package com.swedbankpay.vas.demo.service;

import com.swedbankpay.vas.demo.domain.swedbankpay.request.GiftCardDepositRequest;
import com.swedbankpay.vas.demo.domain.swedbankpay.request.GiftCardRequest;
import com.swedbankpay.vas.demo.domain.swedbankpay.response.GiftCardResponse;
import com.swedbankpay.vas.demo.domain.swedbankpay.response.PreDepositResponse;
import com.swedbankpay.vas.demo.repository.external.VasPaymentApiRepository;
import com.swedbankpay.vas.demo.util.error.BadRequestException;
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
