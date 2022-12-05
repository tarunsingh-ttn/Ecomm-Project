package com.TTN.Ecommerce.dto.category;

import lombok.Data;

@Data
public class CreateCategory {
    private String name;
    private Long parentId;
}
