package com.TTN.Ecommerce.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateVariation {
    private Long prodId;
    private Map<String,String> metadata;
    private Float price;
    private Integer quantity;
//    private String imageName;

}
