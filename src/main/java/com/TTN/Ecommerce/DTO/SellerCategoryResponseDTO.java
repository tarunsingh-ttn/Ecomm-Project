package com.TTN.Ecommerce.DTO;

import com.TTN.Ecommerce.Entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SellerCategoryResponseDTO {
    private Long id;
    private String name;
    private Category parent;
    private List<MetadataResponseDTO> metadata;
}
