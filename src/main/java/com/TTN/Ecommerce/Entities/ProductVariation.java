package com.TTN.Ecommerce.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prodVar_gen")
    @SequenceGenerator(name = "prodVar_gen", sequenceName = "prodVar_seq", initialValue = 1, allocationSize=1)
    private long prodVarId;

    private long quantAvailable;

    private long price;

    private String metadata;

    private String imageName;

    private boolean IS_ACTIVE;

    @ManyToOne
    @JoinColumn(name="prodId")
    private Product product;

}
