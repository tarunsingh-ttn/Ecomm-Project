package com.TTN.Ecommerce.dto.Category;

import lombok.Data;

@Data
public class CreateCategory {
    private String name;
    private Long parentId;
}
