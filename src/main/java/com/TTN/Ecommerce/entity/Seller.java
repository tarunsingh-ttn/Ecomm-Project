package com.TTN.Ecommerce.entity;


import com.TTN.Ecommerce.utils.Auditable;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Data
public class Seller extends Auditable<String> {
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
    @OneToOne(mappedBy = "seller",cascade = CascadeType.ALL)
    @JsonManagedReference("seller-address")
    private Address address;
}
