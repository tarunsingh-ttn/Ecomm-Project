package com.TTN.Ecommerce.Services;

import com.TTN.Ecommerce.DTO.CreateCategory;
import com.TTN.Ecommerce.Entities.Category;
import com.TTN.Ecommerce.Exception.EcommerceException;
import com.TTN.Ecommerce.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public String addCategory(CreateCategory category) throws EcommerceException {
        Category newCategory=new Category();
        newCategory.setName(category.getName());
        if(category.getParentId()!=null){
            Category parentCategory=categoryRepository.findById(category.getParentId())
                    .orElseThrow(()->new EcommerceException("No parent Category Found with this id", HttpStatus.NOT_FOUND));
            newCategory.setParent(parentCategory);
        }
        Long id=categoryRepository.save(newCategory).getCatId();
        return "Category added with id "+id;
    }

    public Category viewCategory(Long categoryId) {
        Category c=categoryRepository.findById(categoryId).get();

        return c;


    }

    public List<Category> viewAllCategories(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
        List<Category> categoryList=new ArrayList<>();
        Category root=new Category();
        root=categoryRepository.findParentNode();
        categoryList.add(root);
        


        return List.of();
    }
}
