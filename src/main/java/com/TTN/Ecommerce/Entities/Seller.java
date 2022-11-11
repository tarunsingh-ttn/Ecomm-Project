package com.TTN.Ecommerce.Entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@DiscriminatorValue("sel")
@Entity
public class Seller extends User{
    private String gst;
    private String companyContact;
    private String companyName;

    @OneToOne
    private Address address;

}
