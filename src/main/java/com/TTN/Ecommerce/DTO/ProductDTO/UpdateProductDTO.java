package com.TTN.Ecommerce.DTO.ProductDTO;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UpdateProductDTO {
    private String description;
    private Boolean IS_CANCELLABLE;
    private Boolean IS_RETURNABLE;
    private String name;

}
