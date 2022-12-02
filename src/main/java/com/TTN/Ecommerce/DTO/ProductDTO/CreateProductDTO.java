package com.TTN.Ecommerce.DTO.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDTO {
    private String description="";
    private boolean IS_CANCELLABLE=false;
    private boolean IS_RETURNABLE=false;
    private String name;
    private String brand;
    private Long catId;
}
