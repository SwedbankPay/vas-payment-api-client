package com.payex.vas.demo.domain.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "payment_account")
public class PaymentAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "external_account_id", unique = true)
    private String externalAccountId;

    @NotBlank
    @Size(max = 19)
    private String pan;

    @NotBlank
    @Size(max = 3)
    private String cvc;

}
