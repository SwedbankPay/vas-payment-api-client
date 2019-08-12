package com.payex.vas.demo.rest.api;

import com.payex.vas.demo.domain.entities.Product;
import com.payex.vas.demo.repository.ProductRepository;
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
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<Product>> listProducts() {
        return ControllerExecutorHelper.executeAndLogRequest(log, "listProducts", () -> {
            var productList = productRepository.findAll();
            return ResponseEntity.ok(productList);
        });
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "getProduct", () -> {
            var product = productRepository.findById(id);
            return ResponseEntity.of(product);
        });
    }


    @PostMapping
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product multiPayProduct) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "addProduct", multiPayProduct, () -> {
            var persisted = productRepository.save(multiPayProduct);
            return ResponseEntity
                .created(URI.create(getCurrentRequestUri() + "/" + persisted.getProductId()))
                .body(persisted);
        });
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product multiPayProduct) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "updateProduct", multiPayProduct, () -> {
            var persisted = productRepository.save(multiPayProduct);
            return ResponseEntity.ok(persisted);
        });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id) {
        return ControllerExecutorHelper.executeAndLogRequest(log, "deleteProduct", () -> {
            productRepository.deleteById(id);
            return ResponseEntity.ok().build();
        });
    }

    private static String getCurrentRequestUri() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI();
    }
}

