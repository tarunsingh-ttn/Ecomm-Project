package com.TTN.Ecommerce.dto.ProductDTO;

import com.TTN.Ecommerce.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ViewVariation {
    private Long varId;
    private Map<String,String> metadata;
    private Float price;
    private Integer quantity;
    private Product product;
}
