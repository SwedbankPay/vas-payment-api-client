package com.payex.vas.demo.rest.api;

import com.payex.vas.demo.domain.entities.PaymentInstrument;
import com.payex.vas.demo.repository.PaymentInstrumentRepository;
import com.payex.vas.demo.rest.util.ControllerExecutorHelper;
import com.payex.vas.demo.service.PaymentService;
import com.payex.vas.demo.util.Constants;
import com.payex.vas.demo.util.error.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/payment-instruments")
@RequiredArgsConstructor
public class PaymentInstrumentController {

    private final PaymentInstrumentRepository paymentInstrumentRepository;
    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<PaymentInstrument>> listPaymentInstrument() {
        return ControllerExecutorHelper.executeAndLogRequest(log, "listPaymentInstrument", () -> {
            var paymentInstrumentList = paymentInstrumentRepository.findAll();
            return ResponseEntity.ok(paymentInstrumentList);
        });
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentInstrument> getPaymentInstrument(@PathVariable Long id) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "getPaymentInstrument", () -> {
            var paymentInstrument = paymentInstrumentRepository.findById(id);
            return ResponseEntity.of(paymentInstrument);
        });
    }
    
    @PostMapping
    public ResponseEntity<PaymentInstrument> addPaymentInstrument(@RequestBody PaymentInstrument paymentInstrument) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "addPaymentInstrument", paymentInstrument, () -> {
            populateExternalAccountIdFromBalanceIfEmpty(paymentInstrument);
            var persisted = paymentInstrumentRepository.save(paymentInstrument);
            return ResponseEntity
                .created(URI.create(getCurrentRequestUri() + "/" + persisted.getId()))
                .body(persisted);
        });
    }

    @PutMapping
    public ResponseEntity<PaymentInstrument> updatePaymentInstrument(@Valid @RequestBody PaymentInstrument paymentInstrument) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "updatePaymentInstrument", paymentInstrument, () -> {
            var persisted = paymentInstrumentRepository.save(paymentInstrument);
            return ResponseEntity.ok(persisted);
        });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePaymentInstrument(@PathVariable Long id) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "deletePaymentInstrument", () -> {
            paymentInstrumentRepository.deleteById(id);
            return ResponseEntity.ok().build();
        });
    }

    private void populateExternalAccountIdFromBalanceIfEmpty(PaymentInstrument paymentInstrument) {
        if (paymentInstrument.getExternalAccountId() == null) {
            try {
                var balanceResponse = paymentService.balance(paymentInstrument, Constants.DEFAULT_AGREEMENT_ID);
                paymentInstrument.setExternalAccountId(balanceResponse.getPaymentAccount().getAccountIdentifier().getAccountId());
            } catch (Exception ex) {
                throw new BadRequestException("Failed while invoking balance request against PayEx: " + ex.getMessage());
            }
        }
    }

    private static String getCurrentRequestUri() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI();
    }
}
