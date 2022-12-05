package com.TTN.Ecommerce.dto.product;

import com.TTN.Ecommerce.entity.Category;
import com.TTN.Ecommerce.entity.ProductVariation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CustomerViewProduct {
    private Long prodId;
    private String name;
    private String description;
    private String brand;
    private Boolean isActive;
    private Boolean isCancellable;
    private Boolean isReturnable;
    private Category category;
    private List<CustomerViewVariation> variations;
}
