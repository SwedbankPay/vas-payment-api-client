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
    private final OrderRepository orderRepository; //TODO:: Ville flyttet denne og all logikk for å lese/skrive fra db inn i MultiPayService klassen. Da holer vi controller klassen ryddig og oversiktlig


    //Only used for local database
    @GetMapping("/order")
    public ResponseEntity<List<OrderRequest>> listOrder() { //TODO:: Hvorfor returneres OrderRequest request og ikke OrderResponse?
        return ControllerExecutorHelper.executeAndLogRequest(log, "listOrder", () -> {
            var orderList = orderRepository.findAll();
             List<OrderRequest> requestList = new ArrayList<>();
            for (var order: orderList) {
                var request = JsonUtil.mapToObject(order.getData(), OrderRequest.class);
                request.setOrderId(order.getId().toString()); //TODO:: Nullsjekk/feilhåndtering
                requestList.add(request);
            }
            return ResponseEntity.ok(requestList);
        });
    }

    @PostMapping("/order")
    public ResponseEntity<OrderRequest> createOrder(@RequestBody OrderRequest request, @RequestHeader String agreementId) { //TODO:: Hvorfor returneres OrderRequest request og ikke OrderResponse?
        request.setPaymentTransmissionDateTime(ZonedDateTime.now()); //TODO:: Alt dette må flyttes ned inni suplier til "executeAndLogRequest".. Ellers er det ingen feilhåndtering for exceptions etc.
        request.setPaymentExpireDateTime(ZonedDateTime.now().plusDays(2L));
        var multiPayOrder = OrderBuilder.buildOrder(request);
        orderRepository.save(multiPayOrder);
        return ControllerExecutorHelper.executeAndLogRequest(log, "post", request, () -> { //TODO:: bruk riktig metodenavn her, "post" er ikke veldig beskrivende
            var orderResponse = multiPayService.createOrder(request, agreementId);

            return ResponseEntity.ok(request);
        });
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderRequest> getOrder(@PathVariable Long orderId, @RequestHeader String agreementId) {//TODO:: Hvorfor returneres OrderRequest request og ikke OrderResponse?
        var order = orderRepository.getOne(orderId); //TODO:: Flytt ned inni suplier til "executeAndLogRequest".. Ellers er det ingen feilhåndtering for exceptions etc.
        var response = JsonUtil.mapToObject(order.getData(),OrderRequest.class); //TODO:: Se kommentar i OrderBuilder, kan bruke denne for å konvertere tilbake
        return ControllerExecutorHelper.executeAndLogRequest(log, "get", response, () -> { //TODO:: bruk riktig metodenavn her, "get" er ikke veldig beskrivende
            var orderResponse = multiPayService.getOrder(orderId, agreementId);
            return ResponseEntity.ok(response);
        });
    }

    @PostMapping("/order/{orderId}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long orderId, @RequestHeader String agreementId) {//TODO:: Hvorfor returneres OrderRequest request og ikke OrderResponse?
        var multipayOrder = orderRepository.getOne(orderId);//TODO:: Flytt ned inni suplier til "executeAndLogRequest".. Ellers er det ingen feilhåndtering for exceptions etc.
        orderRepository.delete(multipayOrder); //TODO:: Why? Denne burde vel oppdatere status på ordre (og helst etter den har kallt backend, ikke før (i tilfelle det tryner))
        return ControllerExecutorHelper.executeAndLogRequest(log, "post", orderId, () -> { //TODO:: bruk riktig metodenavn her, "post" er ikke veldig beskrivende
            var orderResponse = multiPayService.cancelOrder(orderId, agreementId);
            return ResponseEntity.ok(orderResponse);
        });
    }

    @GetMapping("/payments")
    public ResponseEntity<List<GenericPaymentResponse>> listPaymentsForPaymentInstrument(@PathVariable(value = "id") Long paymentInstrumentId) { ///TODO:: Dette kan ikke være riktig.. Denne må være en del av url dersom den skal tas inn
        return ControllerExecutorHelper.executeAndLogRequest(log, "listPaymentsForPaymentInstrument", () -> {
            var paymentResponseList = multiPayService.listPaymentsForPaymentInstrument(paymentInstrumentId);
            return ResponseEntity.ok(paymentResponseList);
        });
    }

    @PostMapping("/deposit")
    public ResponseEntity<GenericPaymentResponse> deposit(@RequestBody GenericPaymentRequest request) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "deposit", request, () -> {
            var paymentInstrumentId = 0L; // Placeholder until we decide if we use it or not for MultiPay //TODO:: Dette må bestemmes før denne klienten kan deles ut...
            var paymentResponse = multiPayService.deposit(paymentInstrumentId, request);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/credit")
    public ResponseEntity<GenericPaymentResponse> credit(@PathVariable(value = "id") Long paymentInstrumentId, //TODO:: Dette kan ikke være riktig.. Denne må være en del av url dersom den skal tas inn
                                                         @RequestBody GenericPaymentRequest request) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "credit", request, () -> {
            var paymentResponse = multiPayService.credit(paymentInstrumentId, request);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/authorize")
    public ResponseEntity<GenericPaymentResponse> authorize(@PathVariable(value = "id") Long paymentInstrumentId,//TODO:: Dette kan ikke være riktig.. Denne må være en del av url dersom den skal tas inn
                                                            @RequestBody GenericPaymentRequest request) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "authorize", request, () -> {
            var paymentResponse = multiPayService.authorize(paymentInstrumentId, request);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/purchase")
    public ResponseEntity<GenericPaymentResponse> purchase(@PathVariable(value = "id") Long paymentInstrumentId, //TODO:: Dette kan ikke være riktig.. Denne må være en del av url dersom den skal tas inn
                                                           @RequestBody GenericPaymentRequest request) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "purchase", request, () -> {
            var paymentResponse = multiPayService.purchase(paymentInstrumentId, request);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/payments/{paymentId}/capture")
    public ResponseEntity<GenericPaymentResponse> capture(@PathVariable(value = "id") Long paymentInstrumentId, //TODO:: Dette kan ikke være riktig.. Denne må være en del av url dersom den skal tas inn
                                                          @PathVariable(value = "paymentId") Long paymentId) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "capture", () -> {
            var paymentResponse = multiPayService.capture(paymentInstrumentId, paymentId);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/payments/{paymentId}/reversal")
    public ResponseEntity<GenericPaymentResponse> reversal(@PathVariable(value = "id") Long paymentInstrumentId, //TODO:: Dette kan ikke være riktig.. Denne må være en del av url dersom den skal tas inn
                                                           @PathVariable(value = "paymentId") Long paymentId) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "reversal", () -> {
            var paymentResponse = multiPayService.reversal(paymentInstrumentId, paymentId);
            return ResponseEntity.ok(paymentResponse);
        });
    }

    @PostMapping("/payments/{paymentId}/cancel")
    public ResponseEntity<GenericPaymentResponse> cancel(@PathVariable(value = "id") Long paymentInstrumentId, //TODO:: Dette kan ikke være riktig.. Denne må være en del av url dersom den skal tas inn
                                                         @PathVariable(value = "paymentId") Long paymentId) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "cancel", () -> {
            var paymentResponse = multiPayService.cancel(paymentInstrumentId, paymentId);
            return ResponseEntity.ok(paymentResponse);
        });
    }

}
