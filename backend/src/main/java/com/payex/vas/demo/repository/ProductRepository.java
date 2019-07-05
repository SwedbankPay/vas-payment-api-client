package com.payex.vas.demo.repository;

import com.payex.vas.demo.domain.entities.MultipayProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<MultipayProduct, Long> {
}
