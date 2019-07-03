package com.payex.vas.demo.domain.entities;

import com.payex.vas.demo.domain.entities.enums.CurrencyIso;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "merchant")
public class SimulatedMerchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String merchantName;

    /**
     * Optional fields for multiPay
     */
    private String sellerName;
    private String sellerId;
    private String terminalId;

    /**
     * Provided by PayEx
     */
    @NotBlank
    private String agreementId;

    @Enumerated(EnumType.STRING)
    private CurrencyIso currencyIso;
}
