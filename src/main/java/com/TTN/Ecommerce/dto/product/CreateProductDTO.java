package com.TTN.Ecommerce.dto.ProductDTO;

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
    private Boolean IS_CANCELLABLE=false;
    private Boolean IS_RETURNABLE=false;
//    private MultipartFile image;
    private String name;
    private String brand;
    private Long catId;
}
