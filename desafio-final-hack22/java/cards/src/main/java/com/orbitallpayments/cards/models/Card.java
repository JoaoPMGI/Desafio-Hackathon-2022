package com.orbitallpayments.cards.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer cardNumber;
    private String embossName;
    private String customerName;
    private Integer documentNumber;
    private String motherName;
    private String address;
    private String city;

}