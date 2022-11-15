package com.TTN.Ecommerce.Entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@Entity
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seller_gen")
    @SequenceGenerator(name="seller_gen", sequenceName = "seller_seq", initialValue = 1, allocationSize = 1)
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
