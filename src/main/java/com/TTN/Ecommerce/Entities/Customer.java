package com.TTN.Ecommerce.Entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue
    private long cust_id;
    private String contact;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id",referencedColumnName = "user_id")
    private User user;

    @OneToMany(mappedBy ="customer")
    private Set<Address> addresses;


}
