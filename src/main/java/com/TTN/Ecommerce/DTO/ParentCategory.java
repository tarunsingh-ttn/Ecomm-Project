package com.TTN.Ecommerce.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParentCategory {
    private long parentId;
    private String name;
    private ParentCategory parent;
}
