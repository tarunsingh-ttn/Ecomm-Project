package com.TTN.Ecommerce.dto.ProductDTO;

import lombok.*;

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
