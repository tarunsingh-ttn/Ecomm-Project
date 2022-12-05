package com.TTN.Ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMetaFieldValue {
    private Long categoryId;
    private Long metaFieldId;
    private List<String> values;
}
