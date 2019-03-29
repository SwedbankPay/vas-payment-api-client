package com.payex.vas.demo.rest.api;

import com.payex.vas.demo.domain.entities.SimulatedMerchant;
import com.payex.vas.demo.repository.SimulatedMerchantRepository;
import com.payex.vas.demo.rest.util.ControllerExecutorHelper;
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
public class MerchantController {

    private final SimulatedMerchantRepository simulatedMerchantRepository;

    @GetMapping("/merchants")
    public ResponseEntity<List<SimulatedMerchant>> listMerchants() {
        return ControllerExecutorHelper.executeAndLogRequest(log, "listMerchants",
            () -> ResponseEntity.ok(simulatedMerchantRepository.findAll()));
    }

    @GetMapping("/merchants/{id}")
    public ResponseEntity<SimulatedMerchant> getMerchant(@PathVariable Long id) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "getMerchant",
            () -> ResponseEntity.of(simulatedMerchantRepository.findById(id)));
    }


    @PostMapping("/merchants")
    public ResponseEntity<SimulatedMerchant> addMerchant(@Valid @RequestBody SimulatedMerchant simulatedMerchant) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "addMerchant", simulatedMerchant, () -> {
            SimulatedMerchant persisted = simulatedMerchantRepository.save(simulatedMerchant);
            return ResponseEntity.created(URI.create(getCurrentRequestUri() + "/" + persisted.getId())).body(persisted);
        });
    }

    @PutMapping("/merchants")
    public ResponseEntity<SimulatedMerchant> updateMerchant(@Valid @RequestBody SimulatedMerchant simulatedMerchant) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "updateMerchant", simulatedMerchant,
            () -> ResponseEntity.ok(simulatedMerchantRepository.save(simulatedMerchant)));
    }

    @DeleteMapping("/merchants/{id}")
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
