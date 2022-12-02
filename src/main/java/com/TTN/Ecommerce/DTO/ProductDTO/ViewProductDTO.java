package com.TTN.Ecommerce.DTO.ProductDTO;

import com.TTN.Ecommerce.Entities.Category;
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
    private boolean isActive;
    private Category category;
}
