package com.TTN.Ecommerce.Entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

public class ProductVariation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prodVar_gen")
    @SequenceGenerator(name = "prodVar_gen", sequenceName = "prodVar_seq", initialValue = 1, allocationSize=1)
    private long prodVarId;

    private long quantAvailable;

    private long price;

    private boolean IS_ACTIVE;



}
