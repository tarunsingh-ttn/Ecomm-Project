package com.TTN.Ecommerce.DTO.Category;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParentCategory {
    private long parentId;
    private String name;
    private ParentCategory parent;
}
