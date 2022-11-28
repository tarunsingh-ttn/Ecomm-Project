package com.TTN.Ecommerce.Entities;

import com.TTN.Ecommerce.utils.Auditable;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class Customer extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "customer_gen")
    @SequenceGenerator(name="customer_gen", sequenceName = "customer_seq", initialValue = 1, allocationSize = 1)
    private long cust_id;
    private String contact;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id",referencedColumnName = "user_id")
    private User user;

    @OneToMany(mappedBy ="customer",cascade = CascadeType.ALL)
    @JsonManagedReference("customer-address")
    private Set<Address> addresses;


}
