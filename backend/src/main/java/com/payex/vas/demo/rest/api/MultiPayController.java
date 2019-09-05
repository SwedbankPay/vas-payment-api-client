package com.payex.vas.demo.rest.api;

import com.payex.vas.demo.domain.payex.request.OrderRequest;
import com.payex.vas.demo.domain.payex.response.OrderResponse;
import com.payex.vas.demo.rest.util.ControllerExecutorHelper;
import com.payex.vas.demo.service.MultiPayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/multipay/")
public class MultiPayController {

    private final MultiPayService multiPayService;

    //Only used for local database
    @GetMapping("/order")
    public ResponseEntity listOrder() {
        return ControllerExecutorHelper.executeAndWrapResponse("listOrder",  multiPayService::listOrder);
    }

    @PostMapping("/order")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request, @RequestHeader String agreementId) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "createOrder", request, () ->
            ResponseEntity.ok(multiPayService.createOrder(request, agreementId))
        );
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderRequest> getOrder(@PathVariable Long orderId, @RequestHeader String agreementId) {

        return ControllerExecutorHelper.executeAndLogRequest(log, "getOrder", () ->
            ResponseEntity.ok(multiPayService.getOrder(orderId, agreementId))
        );
    }

    @PostMapping("/order/{orderId}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long orderId, @RequestHeader String agreementId) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "cancelOrder", orderId, () ->
            ResponseEntity.ok(multiPayService.cancelOrder(orderId, agreementId))
        );
    }
}
