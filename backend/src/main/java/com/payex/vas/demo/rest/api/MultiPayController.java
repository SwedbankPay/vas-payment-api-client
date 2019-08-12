package com.payex.vas.demo.rest.api;

import com.payex.vas.demo.domain.dto.GenericPaymentRequest;
import com.payex.vas.demo.domain.dto.GenericPaymentResponse;
import com.payex.vas.demo.domain.payex.request.OrderRequest;
import com.payex.vas.demo.domain.payex.response.OrderResponse;
import com.payex.vas.demo.rest.util.ControllerExecutorHelper;
import com.payex.vas.demo.service.MultiPayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@EnableSpringDataWebSupport
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

    @GetMapping("/payments/{paymentInstrumentId}")
    public ResponseEntity<List<GenericPaymentResponse>> listPaymentsForPaymentInstrument(@PathVariable(value = "id") Long paymentInstrumentId) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "listPaymentsForPaymentInstrument", () ->
            ResponseEntity.ok(multiPayService.listPaymentsForPaymentInstrument(paymentInstrumentId))
        );
    }

    @PostMapping("/deposit") //Should we even support this?
    public ResponseEntity<GenericPaymentResponse> deposit(@RequestBody GenericPaymentRequest request) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "deposit", request, () -> {
            var paymentResponse = multiPayService.deposit(0L, request);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/credit")
    public ResponseEntity<GenericPaymentResponse> credit(@RequestBody GenericPaymentRequest request) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "credit", request, () -> {
            var paymentResponse = multiPayService.credit(0L, request);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/authorize")
    public ResponseEntity<GenericPaymentResponse> authorize(@RequestBody GenericPaymentRequest request) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "authorize", request, () -> {
            var paymentResponse = multiPayService.authorize(0L, request);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/purchase")
    public ResponseEntity<GenericPaymentResponse> purchase(@RequestBody GenericPaymentRequest request) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "purchase", request, () -> {
            var paymentResponse = multiPayService.purchase(0L, request);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/payments/{paymentId}/capture")
    public ResponseEntity<GenericPaymentResponse> capture(@PathVariable(value = "paymentId") Long paymentId) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "capture", () -> {
            var paymentResponse = multiPayService.capture(0L, paymentId);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/payments/{paymentId}/reversal")
    public ResponseEntity<GenericPaymentResponse> reversal(@PathVariable(value = "paymentId") Long paymentId) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "reversal", () -> {
            var paymentResponse = multiPayService.reversal(0L, paymentId);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/payments/{paymentId}/cancel")
    public ResponseEntity<GenericPaymentResponse> cancel(@PathVariable(value = "paymentId") Long paymentId) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "cancel", () -> {
            var paymentResponse = multiPayService.cancel(0L, paymentId);
            return ResponseEntity.ok(paymentResponse);
        });
    }

}
