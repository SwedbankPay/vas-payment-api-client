package com.swedbankpay.vas.demo.rest.api;

import com.swedbankpay.vas.demo.domain.swedbankpay.request.PingRequest;
import com.swedbankpay.vas.demo.domain.swedbankpay.response.PingResponse;
import com.swedbankpay.vas.demo.service.PingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ping")
@RequiredArgsConstructor
public class PingController {
    private final PingService pingService;

    @PostMapping
    public ResponseEntity<PingResponse> pingPong(@RequestBody PingRequest request){
        return ResponseEntity.ok(pingService.pingPong(request));
    }
}
