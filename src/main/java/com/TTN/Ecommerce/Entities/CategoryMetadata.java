package com.TTN.Ecommerce.Entities;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class CategoryMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "metadata_gen")
    @SequenceGenerator(name="metadata_gen", sequenceName = "metadata_seq", initialValue = 1, allocationSize = 1)
    private long metaId;
    @Column(unique = true)
    private String name;


}
