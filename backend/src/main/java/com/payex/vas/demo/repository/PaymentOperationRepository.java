package com.payex.vas.demo.repository;

import com.payex.vas.demo.domain.entities.PaymentOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentOperationRepository extends JpaRepository<PaymentOperation, Long> {
}
