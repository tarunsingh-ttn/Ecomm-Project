package com.TTN.Ecommerce.Entities;


import com.TTN.Ecommerce.utils.Auditable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;


@Entity
@Getter
@Setter
public class Address extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_gen")
    @SequenceGenerator(name = "address_gen", sequenceName = "address_seq", initialValue = 1, allocationSize = 1 )
    private long address_id;
    private String city;
    private String state;
    private String country;
    private String addressLine;
    private String  zipCode;
    private String label;
    @OneToOne
    @JoinColumn(name="seller_id")
    @JsonBackReference("seller-address")
    private Seller seller;
    @ManyToOne
    @JoinColumn(name="cust_id")
    @JsonBackReference("customer-address")
    private Customer customer;

}

