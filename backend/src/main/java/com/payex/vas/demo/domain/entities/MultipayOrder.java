package com.payex.vas.demo.domain.entities;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "multipay_order")
public class MultipayOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "data")
    private String data;
}
