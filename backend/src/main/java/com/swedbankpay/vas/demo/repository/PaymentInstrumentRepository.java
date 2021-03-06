package com.swedbankpay.vas.demo.repository;

import com.swedbankpay.vas.demo.domain.entities.PaymentInstrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentInstrumentRepository extends JpaRepository<PaymentInstrument, Long> {
}
