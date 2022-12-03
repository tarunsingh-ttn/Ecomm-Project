package com.TTN.Ecommerce.Entities;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryMetaValue {
    @EmbeddedId
    private CategoryMetadataFieldKey categoryMetadataFieldKey;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("category_id")
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("category_metadata_field_id")
    @JoinColumn(name = "category_metadata_field_id")
    private CategoryMetadata categoryMetadataField;
    private String value;
}
