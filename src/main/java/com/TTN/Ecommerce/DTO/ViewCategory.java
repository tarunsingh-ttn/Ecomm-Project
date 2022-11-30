package com.TTN.Ecommerce.DTO;


import com.TTN.Ecommerce.Entities.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ViewCategory {

    private long catId;
    private String name;
    private Category parent;
    private Set<ChildCategoryDTO> children = new HashSet<>();
}
