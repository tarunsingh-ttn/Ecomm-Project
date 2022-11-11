package com.TTN.Ecommerce.Entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue
    private long address_id;
    private String city;
    private String state;
    private String country;
    private String addressLine;
    private long zipCode;
    private String label;

//    @OneToOne
//    @MapsId
//    private Seller seller;



}

