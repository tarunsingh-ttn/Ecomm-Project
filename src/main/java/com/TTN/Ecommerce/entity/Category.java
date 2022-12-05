package com.TTN.Ecommerce.Entities;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "category_gen")
    @SequenceGenerator(name="category_gen", sequenceName = "category_seq", initialValue = 1, allocationSize = 1)
    private long catId;
    private String name;
    @JoinColumn(name = "parent_id")
    @ManyToOne
    private Category parent;
    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    private Set<Category> children = new HashSet<>();
    private static final List<String> allChildren=new ArrayList<>();
    public List<String> getAllChildren(Category parent,List<String> childList){
        for(Category child: parent.getChildren()){
            allChildren.add(child.getName());
            getAllChildren(child,allChildren);
        }
        return allChildren;
    }
}
