package com.payex.vas.demo.domain.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "multipay_products")
public class MultipayProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private Long productId;

    @NotBlank
    @Column(name = "productOrderId")
    private Long productOrderId;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @NotBlank
    @Column(name = "quantity")
    private BigInteger quantity;

    @NotBlank
    @Column(name = "unitOfMeasure")
    private String unitOfMeasure;

    @NotBlank
    @Column(name = "vatAmount")
    private BigInteger vatAmount;

    @NotBlank
    @Column(name = "vatRate")
    private BigDecimal vatRate;
}

