package com.payex.vas.demo.repository;

import com.payex.vas.demo.domain.entities.MultipayOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<MultipayOrder, Long> {
}
