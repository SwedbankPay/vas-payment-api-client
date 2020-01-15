package com.payex.vas.demo.service;

import com.payex.vas.demo.domain.payex.request.PingRequest;
import com.payex.vas.demo.domain.payex.response.PingResponse;
import com.payex.vas.demo.repository.external.VasPaymentApiRepository;
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
