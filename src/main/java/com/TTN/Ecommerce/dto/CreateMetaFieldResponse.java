package com.TTN.Ecommerce.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMetaFieldResponse {
    private Long categoryId;
    private Long metaFieldId;
    private String values;
}
