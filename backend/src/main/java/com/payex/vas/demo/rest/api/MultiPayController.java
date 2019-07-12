package com.payex.vas.demo.rest.api;

import com.payex.vas.demo.domain.dto.GenericPaymentRequest;
import com.payex.vas.demo.domain.dto.GenericPaymentResponse;
import com.payex.vas.demo.domain.entities.MultipayOrder;
import com.payex.vas.demo.domain.payex.request.OrderRequest;
import com.payex.vas.demo.domain.payex.response.OrderResponse;
import com.payex.vas.demo.repository.OrderRepository;
import com.payex.vas.demo.rest.util.ControllerExecutorHelper;
import com.payex.vas.demo.service.MultiPayService;
import com.payex.vas.demo.util.JsonUtil;
import com.payex.vas.demo.util.helper.OrderBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/multipay/")
public class MultiPayController {

    private final MultiPayService multiPayService;
    private final OrderRepository orderRepository;


    //Only used for local database
    @GetMapping("/order")
    public ResponseEntity<List<OrderRequest>> listOrder() {
        return ControllerExecutorHelper.executeAndLogRequest(log, "listOrder", () -> {
            var orderList = orderRepository.findAll();
             List<OrderRequest> requestList = new ArrayList<>();
            for (var order: orderList) {
                var request = JsonUtil.mapToObject(order.getData(), OrderRequest.class);
                request.setOrderId(order.getId().toString());
                requestList.add(request);
            }
            return ResponseEntity.ok(requestList);
        });
    }

    @PostMapping("/order")
    public ResponseEntity<OrderRequest> createOrder(@RequestBody OrderRequest request, @RequestHeader String agreementId) {
        request.setPaymentTransmissionDateTime(ZonedDateTime.now());
        request.setPaymentExpireDateTime(ZonedDateTime.now().plusDays(2L));
        var multiPayOrder = OrderBuilder.buildOrder(request);
        orderRepository.save(multiPayOrder);
        return ControllerExecutorHelper.executeAndLogRequest(log, "post", request, () -> {
            var orderResponse = multiPayService.createOrder(request, agreementId);

            return ResponseEntity.ok(request);
        });
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderRequest> getOrder(@PathVariable Long orderId, @RequestHeader String agreementId) {
        var order = orderRepository.getOne(orderId);
        var response = JsonUtil.mapToObject(order.getData(),OrderRequest.class);
        return ControllerExecutorHelper.executeAndLogRequest(log, "get", response, () -> {
            var orderResponse = multiPayService.getOrder(orderId, agreementId);
            return ResponseEntity.ok(response);
        });
    }

    @PostMapping("/order/{orderId}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long orderId, @RequestHeader String agreementId) {
        var multipayOrder = orderRepository.getOne(orderId);
        orderRepository.delete(multipayOrder);
        return ControllerExecutorHelper.executeAndLogRequest(log, "post", orderId, () -> {
            var orderResponse = multiPayService.cancelOrder(orderId, agreementId);
            return ResponseEntity.ok(orderResponse);
        });
    }

    @GetMapping("/payments")
    public ResponseEntity<List<GenericPaymentResponse>> listPaymentsForPaymentInstrument(@PathVariable(value = "id") Long paymentInstrumentId) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "listPaymentsForPaymentInstrument", () -> {
            var paymentResponseList = multiPayService.listPaymentsForPaymentInstrument(paymentInstrumentId);
            return ResponseEntity.ok(paymentResponseList);
        });
    }

    @PostMapping("/deposit")
    public ResponseEntity<GenericPaymentResponse> deposit(@RequestBody GenericPaymentRequest request) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "deposit", request, () -> {
            var paymentInstrumentId = 0L; // Placeholder until we decide if we use it or not for MultiPay
            var paymentResponse = multiPayService.deposit(paymentInstrumentId, request);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/credit")
    public ResponseEntity<GenericPaymentResponse> credit(@PathVariable(value = "id") Long paymentInstrumentId,
                                                         @RequestBody GenericPaymentRequest request) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "credit", request, () -> {
            var paymentResponse = multiPayService.credit(paymentInstrumentId, request);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/authorize")
    public ResponseEntity<GenericPaymentResponse> authorize(@PathVariable(value = "id") Long paymentInstrumentId,
                                                            @RequestBody GenericPaymentRequest request) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "authorize", request, () -> {
            var paymentResponse = multiPayService.authorize(paymentInstrumentId, request);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/purchase")
    public ResponseEntity<GenericPaymentResponse> purchase(@PathVariable(value = "id") Long paymentInstrumentId,
                                                           @RequestBody GenericPaymentRequest request) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "purchase", request, () -> {
            var paymentResponse = multiPayService.purchase(paymentInstrumentId, request);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/payments/{paymentId}/capture")
    public ResponseEntity<GenericPaymentResponse> capture(@PathVariable(value = "id") Long paymentInstrumentId,
                                                          @PathVariable(value = "paymentId") Long paymentId) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "capture", () -> {
            var paymentResponse = multiPayService.capture(paymentInstrumentId, paymentId);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/payments/{paymentId}/reversal")
    public ResponseEntity<GenericPaymentResponse> reversal(@PathVariable(value = "id") Long paymentInstrumentId,
                                                           @PathVariable(value = "paymentId") Long paymentId) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "reversal", () -> {
            var paymentResponse = multiPayService.reversal(paymentInstrumentId, paymentId);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/payments/{paymentId}/cancel")
    public ResponseEntity<GenericPaymentResponse> cancel(@PathVariable(value = "id") Long paymentInstrumentId,
                                                         @PathVariable(value = "paymentId") Long paymentId) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "cancel", () -> {
            var paymentResponse = multiPayService.cancel(paymentInstrumentId, paymentId);
            return ResponseEntity.ok(paymentResponse);
        });
    }

}
