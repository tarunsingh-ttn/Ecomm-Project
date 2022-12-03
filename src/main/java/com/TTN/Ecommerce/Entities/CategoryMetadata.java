package com.TTN.Ecommerce.Entities;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class CategoryMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "metadata_gen")
    @SequenceGenerator(name="metadata_gen", sequenceName = "metadata_seq", initialValue = 1, allocationSize = 1)
    private long metaId;
    private String name;
}
