package com.TTN.Ecommerce.Entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Entity
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_gen")
    @SequenceGenerator(name = "address_gen", sequenceName = "address_seq", initialValue = 1, allocationSize = 1 )
    private long address_id;
    private String city;
    private String state;
    private String country;
    private String addressLine;
    private long zipCode;
    private String label;

    @OneToOne
    @JoinColumn(name="seller_id")
    @JsonBackReference
    private Seller seller;

    @ManyToOne
    @JoinColumn(name="cust_id")
    @JsonBackReference
    private Customer customer;

}

