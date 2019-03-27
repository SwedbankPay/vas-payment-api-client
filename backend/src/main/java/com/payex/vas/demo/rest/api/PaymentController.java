package com.payex.vas.demo.rest.api;

import com.payex.vas.demo.domain.dto.GenericPaymentRequest;
import com.payex.vas.demo.domain.dto.GenericPaymentResponse;
import com.payex.vas.demo.rest.util.ControllerExecutorHelper;
import com.payex.vas.demo.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payment-accounts/{id}")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/authorize")
    public ResponseEntity<GenericPaymentResponse> authorize(@PathVariable(value = "id") Long paymentAccountId,
                                                            @RequestBody GenericPaymentRequest request) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "authorize", request, () -> {
            var paymentResponse = paymentService.authorize(paymentAccountId, request);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/purchase")
    public ResponseEntity<GenericPaymentResponse> purchase(@PathVariable(value = "id") Long paymentAccountId,
                                                           @RequestBody GenericPaymentRequest request) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "purchase", request, () -> {
            var paymentResponse = paymentService.purchase(paymentAccountId, request);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/payments/{paymentId}/capture")
    public ResponseEntity<GenericPaymentResponse> capture(@PathVariable(value = "id") Long paymentAccountId, //TODO::
                                                          @PathVariable(value = "paymentId") Long paymentId) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "capture", null, () -> {
            var paymentResponse = paymentService.capture(paymentId);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/payments/{paymentId}/reversal")
    public ResponseEntity<GenericPaymentResponse> reversal(@PathVariable(value = "id") Long paymentAccountId, //TODO::
                                                           @PathVariable(value = "paymentId") Long paymentId) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "reversal", null, () -> {
            var paymentResponse = paymentService.reversal(paymentId);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/payments/{paymentId}/cancel")
    public ResponseEntity<GenericPaymentResponse> cancel(@PathVariable(value = "id") Long paymentAccountId, //TODO::
                                                         @PathVariable(value = "paymentId") Long paymentId) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "cancel", null, () -> {
            var paymentResponse = paymentService.cancel(paymentId);
            return ResponseEntity.ok(paymentResponse);
        });
    }

}
