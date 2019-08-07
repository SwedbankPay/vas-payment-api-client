package com.payex.vas.demo.domain.entities;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "multipay_order") //TODO: Kan fjerne Multipay fra navnet
public class MultipayOrder { //TODO: Kan fjerne Multipay fra navnet

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "data") // TODO: Burde være ha "columnDefinition = "TEXT""
    private String data;
}
