package com.swedbankpay.vas.demo.rest.api;

import com.swedbankpay.vas.demo.domain.entities.SimulatedMerchant;
import com.swedbankpay.vas.demo.repository.SimulatedMerchantRepository;
import com.swedbankpay.vas.demo.rest.util.ControllerExecutorHelper;
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
@RequestMapping("/api/merchants")
@RequiredArgsConstructor
public class MerchantController {

    private final SimulatedMerchantRepository simulatedMerchantRepository;

    @GetMapping
    public ResponseEntity<List<SimulatedMerchant>> listMerchants() {
        return ControllerExecutorHelper.executeAndLogRequest(log, "listMerchants", () -> {
            var merchantList = simulatedMerchantRepository.findAll();
            return ResponseEntity.ok(merchantList);
        });
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimulatedMerchant> getMerchant(@PathVariable Long id) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "getMerchant", () -> {
            var merchant = simulatedMerchantRepository.findById(id);
            return ResponseEntity.of(merchant);
        });
    }


    @PostMapping
    public ResponseEntity<SimulatedMerchant> addMerchant(@Valid @RequestBody SimulatedMerchant simulatedMerchant) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "addMerchant", simulatedMerchant, () -> {
            var persisted = simulatedMerchantRepository.save(simulatedMerchant);
            return ResponseEntity
                .created(URI.create(getCurrentRequestUri() + "/" + persisted.getId()))
                .body(persisted);
        });
    }

    @PutMapping
    public ResponseEntity<SimulatedMerchant> updateMerchant(@Valid @RequestBody SimulatedMerchant simulatedMerchant) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "updateMerchant", simulatedMerchant, () -> {
            var persisted = simulatedMerchantRepository.save(simulatedMerchant);
            return ResponseEntity.ok(persisted);
        });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMerchant(@PathVariable Long id) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "deleteMerchant", () -> {
            simulatedMerchantRepository.deleteById(id);
            return ResponseEntity.ok().build();
        });
    }

    private static String getCurrentRequestUri() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI();
    }
}
