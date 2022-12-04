package com.TTN.Ecommerce.DTO.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

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
