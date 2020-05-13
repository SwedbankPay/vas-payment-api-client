package com.swedbankpay.vas.demo.domain.entities;

import com.swedbankpay.vas.demo.domain.entities.enums.PaymentInstrumentType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "payment_instrument")
public class PaymentInstrument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PaymentInstrumentType type;

    @NotBlank
    @Column(name = "external_account_id", unique = true)
    private String externalAccountId;

    @NotBlank
    @Size(max = 19)
    private String pan;

    @NotBlank
    @Size(max = 3)
    private String cvc;

    private Long balance;

    private OffsetDateTime lastBalanceSync;

}
