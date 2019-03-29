package com.payex.vas.demo.rest.api;

import com.payex.vas.demo.domain.dto.GenericPaymentRequest;
import com.payex.vas.demo.domain.dto.GenericPaymentResponse;
import com.payex.vas.demo.rest.util.ControllerExecutorHelper;
import com.payex.vas.demo.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payment-instruments/{id}")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/payments")
    public ResponseEntity<List<GenericPaymentResponse>> listPaymentsForPaymentInstrument(@PathVariable(value = "id") Long paymentInstrumentId) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "listPaymentsForPaymentInstrument",
            () -> ResponseEntity.ok(paymentService.listPaymentsForPaymentInstrument(paymentInstrumentId)));
    }

    @PostMapping("/authorize")
    public ResponseEntity<GenericPaymentResponse> authorize(@PathVariable(value = "id") Long paymentInstrumentId,
                                                            @RequestBody GenericPaymentRequest request) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "authorize", request, () -> {
            var paymentResponse = paymentService.authorize(paymentInstrumentId, request);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/purchase")
    public ResponseEntity<GenericPaymentResponse> purchase(@PathVariable(value = "id") Long paymentInstrumentId,
                                                           @RequestBody GenericPaymentRequest request) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "purchase", request, () -> {
            var paymentResponse = paymentService.purchase(paymentInstrumentId, request);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/payments/{paymentId}/capture")
    public ResponseEntity<GenericPaymentResponse> capture(@PathVariable(value = "id") Long paymentInstrumentId,
                                                          @PathVariable(value = "paymentId") Long paymentId) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "capture", () -> {
            var paymentResponse = paymentService.capture(paymentInstrumentId, paymentId);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/payments/{paymentId}/reversal")
    public ResponseEntity<GenericPaymentResponse> reversal(@PathVariable(value = "id") Long paymentInstrumentId,
                                                           @PathVariable(value = "paymentId") Long paymentId) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "reversal", () -> {
            var paymentResponse = paymentService.reversal(paymentInstrumentId, paymentId);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/payments/{paymentId}/cancel")
    public ResponseEntity<GenericPaymentResponse> cancel(@PathVariable(value = "id") Long paymentInstrumentId,
                                                         @PathVariable(value = "paymentId") Long paymentId) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "cancel", () -> {
            var paymentResponse = paymentService.cancel(paymentInstrumentId, paymentId);
            return ResponseEntity.ok(paymentResponse);
        });
    }

}
