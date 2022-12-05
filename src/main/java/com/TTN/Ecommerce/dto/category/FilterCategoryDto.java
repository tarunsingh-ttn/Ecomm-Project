package com.TTN.Ecommerce.dto.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class FilterCategoryDto {
    String name;
    List<String> fields;
    List<String> values;
    List<String> brands;
    Float maxPrice;
    Float minPrice;
}
