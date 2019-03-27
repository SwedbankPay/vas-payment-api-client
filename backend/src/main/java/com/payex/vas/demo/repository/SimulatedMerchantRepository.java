package com.payex.vas.demo.repository;

import com.payex.vas.demo.domain.entities.SimulatedMerchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulatedMerchantRepository extends JpaRepository<SimulatedMerchant, Long> {
}
