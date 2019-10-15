package com.payex.vas.demo.rest.api;

import com.payex.vas.demo.domain.payex.request.PingRequest;
import com.payex.vas.demo.domain.payex.response.PingResponse;
import com.payex.vas.demo.service.PingService;
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
        PingResponse response = pingService.pingPong(request);
        return ResponseEntity.ok(response);
    }
}
