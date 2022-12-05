package com.TTN.Ecommerce.dto.ProductDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class UpdateVariation {


    private Map<String,String> metadata;
    private Float price;
    private Integer quantity;
    private Boolean isActive;
    //primaryimage
}
