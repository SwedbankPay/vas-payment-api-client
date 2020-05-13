package com.swedbankpay.vas.demo.domain.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "payment_operation")
public class PaymentOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long paymentInstrumentId;

    @NotNull
    private Long merchantId;

    @NotBlank
    private String transactionType;

    @NotBlank
    private String state;

    @NotNull
    private Long amount;

    @NotBlank
    private String externalPaymentId;

    @NotBlank
    private String externalAccountId;

    @NotBlank
    private String paymentOrderRef;

    @NotBlank
    private String paymentTransactionRef;

    @NotNull
    private OffsetDateTime created;

    @Column(columnDefinition = "TEXT")
    private String jsonResponse;

}
