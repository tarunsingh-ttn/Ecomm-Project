package com.TTN.Ecommerce.Entities;

import javax.persistence.*;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(length = 50000000)
    private byte[] picByte;


}
