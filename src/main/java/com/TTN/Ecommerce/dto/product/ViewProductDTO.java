package com.TTN.Ecommerce.dto.ProductDTO;

import com.TTN.Ecommerce.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewProductDTO {
    private Long prodId;
    private String name;
    private String description;
    private String brand;
    private Boolean isActive;
    private Boolean isCancellable;
    private Boolean isReturnable;
    private Category category;
}
