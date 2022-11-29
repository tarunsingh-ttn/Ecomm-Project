package com.TTN.Ecommerce.Controller;

import com.TTN.Ecommerce.DTO.CreateCategory;
import com.TTN.Ecommerce.Entities.Category;
import com.TTN.Ecommerce.Exception.EcommerceException;
import com.TTN.Ecommerce.Services.CategoryService;
import com.TTN.Ecommerce.Services.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("api/user/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<String> createCategory(@RequestBody CreateCategory category) throws EcommerceException {
                String response=categoryService.addCategory(category);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/view")
    public ResponseEntity<Category> viewCategory(@RequestParam Long categoryId){
        Category categoryList=categoryService.viewCategory(categoryId);
        return new ResponseEntity<>(categoryList,HttpStatus.OK);
    }
    @GetMapping("/view/all")
    public ResponseEntity<List<Category>> viewAllCategory(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                                                     @RequestParam(defaultValue = "id") String sortBy,
                                                                     @Pattern(regexp="DESC|ASC") @RequestParam(required = false) String sortOrder){
        List<Category> fieldList = categoryService.viewAllCategories(pageNo,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(fieldList,HttpStatus.OK);
    }


    
}
