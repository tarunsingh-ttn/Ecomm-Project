package com.TTN.Ecommerce.DTO.ProductDTO;

import com.TTN.Ecommerce.Entities.Category;
import com.TTN.Ecommerce.Entities.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.configurationprocessor.json.JSONObject;

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
