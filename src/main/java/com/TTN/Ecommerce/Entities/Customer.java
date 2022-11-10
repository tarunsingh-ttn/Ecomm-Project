package com.TTN.Ecommerce.Entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "User_ID")
public class Customer extends User{

    @Column(name = "Contact")
    private long contact;


    private Set<Address> addresses;


}
