package com.TTN.Ecommerce.Entities;


import javax.persistence.*;


public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prod_gen")
    @SequenceGenerator(name = "prod_gen", sequenceName = "prod_seq", initialValue = 1, allocationSize=1)
    private long prodId;
    private String name;
    private String description;
    private boolean IS_CANCELLABLE;
    private boolean IS_RETURNABLE;
    private String brand;
    private boolean IS_ACTIVE;
    private boolean IS_DELETED;

}
