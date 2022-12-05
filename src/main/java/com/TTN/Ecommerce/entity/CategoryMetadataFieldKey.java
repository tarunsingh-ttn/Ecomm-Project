package com.TTN.Ecommerce.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;


@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryMetadataFieldKey implements Serializable {
    @Column(name = "category_id")
    private Long categoryId;
    @Column(name = "category_metadata_field_id")
    private Long categoryMetadataFieldId;
}
