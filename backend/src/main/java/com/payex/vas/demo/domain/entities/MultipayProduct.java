package com.payex.vas.demo.domain.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotNull
    @Column(name = "productOrderId")
    private Long productOrderId;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "quantity")
    private BigInteger quantity;

    @NotBlank
    @Column(name = "unitOfMeasure")
    private String unitOfMeasure;

    @NotNull
    @Column(name = "vatAmount")
    private BigInteger vatAmount;

    @NotNull
    @Column(name = "vatRate")
    private BigDecimal vatRate;
}

