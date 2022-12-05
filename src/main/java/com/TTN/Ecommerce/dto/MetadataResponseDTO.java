package com.TTN.Ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetadataResponseDTO {
    private Long metadataId;
    private String fieldName;
    private String possibleValues;
}