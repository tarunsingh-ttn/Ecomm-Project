package com.TTN.Ecommerce.Entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
public class Seller {
    @Id
    @GeneratedValue
    private long seller_id;
    private String gst;
    private String companyContact;
    private String companyName;

    //    @OneToOne(mappedBy = "seller")
//    @MapsId
//    private User user;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;


}
