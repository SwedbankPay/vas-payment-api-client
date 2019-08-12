package com.payex.vas.demo.repository;

import com.payex.vas.demo.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
