package com.TTN.Ecommerce.DTO.Category;

import lombok.Data;

@Data
public class CreateCategory {
    private String name;
    private Long parentId;
}
