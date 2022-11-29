package com.TTN.Ecommerce.DTO;

import lombok.Data;

@Data
public class CreateCategory {
    private String name;
    private Long parentId;
}
