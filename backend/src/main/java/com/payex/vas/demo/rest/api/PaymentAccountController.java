package com.payex.vas.demo.rest.api;

import com.payex.vas.demo.domain.entities.PaymentAccount;
import com.payex.vas.demo.repository.PaymentAccountRepository;
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
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentAccountController {

    private final PaymentAccountRepository paymentAccountRepository;

    @GetMapping("/payment-accounts")
    public List<PaymentAccount> listPaymentAccounts() {
        log.info("Invoked listPaymentAccounts");
        return paymentAccountRepository.findAll();
    }

    @PostMapping("/payment-accounts")
    public ResponseEntity<PaymentAccount> addPaymentAccount(@Valid @RequestBody PaymentAccount paymentAccount) {
        log.info("Invoked addPaymentAccount: {}", paymentAccount);
        PaymentAccount persisted = paymentAccountRepository.save(paymentAccount);
        return ResponseEntity.created(URI.create(getCurrentRequestUri() + "/" + persisted.getId())).body(persisted);
    }

    @PutMapping("/payment-accounts")
    public ResponseEntity<PaymentAccount> updatePaymentAccount(@Valid @RequestBody PaymentAccount paymentAccount) {
        log.info("Invoked updatePaymentAccount: {}", paymentAccount);
        return ResponseEntity.ok(paymentAccountRepository.save(paymentAccount));
    }

    @DeleteMapping("/payment-accounts/{id}")
    public ResponseEntity updatePaymentAccount(@PathVariable Long id) {
        log.info("Invoked deletePaymentAccount: {}", id);
        paymentAccountRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private static String getCurrentRequestUri() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI();
    }
}
