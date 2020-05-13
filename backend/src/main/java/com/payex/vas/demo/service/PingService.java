package com.swedbankpay.vas.demo.service;

import com.swedbankpay.vas.demo.domain.swedbankpay.request.PingRequest;
import com.swedbankpay.vas.demo.domain.swedbankpay.response.PingResponse;
import com.swedbankpay.vas.demo.repository.external.VasPaymentApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PingService {
    private final VasPaymentApiRepository vasPaymentApiRepository;

    public PingResponse pingPong(PingRequest request){
        return vasPaymentApiRepository.pingPong(request);
    }
}
