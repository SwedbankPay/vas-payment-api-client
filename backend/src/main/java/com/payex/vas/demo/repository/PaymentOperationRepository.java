package com.payex.vas.demo.repository;

import com.payex.vas.demo.domain.entities.PaymentOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentOperationRepository extends JpaRepository<PaymentOperation, Long> {
    List<PaymentOperation> findTop100ByPaymentInstrumentIdOrderByCreatedDesc(Long paymentInstrumentId);
}
