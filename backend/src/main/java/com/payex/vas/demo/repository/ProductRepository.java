package com.payex.vas.demo.repository;

import com.payex.vas.demo.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
