package com.TTN.Ecommerce.Entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="seller_id",referencedColumnName = "seller_id")
    private Seller seller;

    @ManyToOne
    @JoinColumn(name="cust_id")
    private Customer customer;

}

