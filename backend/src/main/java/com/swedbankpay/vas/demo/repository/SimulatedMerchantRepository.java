package com.swedbankpay.vas.demo.repository;

import com.swedbankpay.vas.demo.domain.entities.SimulatedMerchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulatedMerchantRepository extends JpaRepository<SimulatedMerchant, Long> {
}
