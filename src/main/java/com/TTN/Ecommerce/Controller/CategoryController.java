package com.TTN.Ecommerce.Controller;

import com.TTN.Ecommerce.DTO.*;
import com.TTN.Ecommerce.Entities.Category;
import com.TTN.Ecommerce.Exception.EcommerceException;
import com.TTN.Ecommerce.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("api/admin/category/add")
    public ResponseEntity<String> createCategory(@RequestBody CreateCategory category) throws EcommerceException {
        String response = categoryService.addCategory(category);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("api/admin/category/view")
    public ResponseEntity<ViewCategory> viewCategory(@RequestParam Long categoryId) {
        ViewCategory categoryResponse = categoryService.viewCategory(categoryId);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }
    @GetMapping("api/admin/category/view/all")
    public ResponseEntity<List<ViewCategory>> viewAllCategory(@RequestParam(defaultValue = "0") Integer pageNo,
                                                              @RequestParam(defaultValue = "10") Integer pageSize,
                                                              @RequestParam(defaultValue = "id") String sortBy,
                                                              @Pattern(regexp="DESC|ASC") @RequestParam(required = false) String sortOrder) {
        List<ViewCategory> fieldList = categoryService.viewAllCategories(pageNo, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(fieldList, HttpStatus.OK);
    }


    @PutMapping("api/admin/category/update")
    public ResponseEntity<String> updateCategory(@RequestBody CategoryUpdateDTO categoryUpdateDTO) throws EcommerceException {
        String response = categoryService.updateCategoryName(categoryUpdateDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


   @PostMapping("api/admin/category/metadata/values")
   public ResponseEntity<CreateMetaFieldResponse> addMetaFieldValues(@RequestBody CreateMetaFieldValue metaFieldValueDTO) throws EcommerceException {
       CreateMetaFieldResponse response = categoryService.addMetaValues(metaFieldValueDTO);
       return new ResponseEntity<>(response,HttpStatus.OK);
   }
    @GetMapping("api/user/category/seller")
    public ResponseEntity<List<SellerCategoryResponseDTO>> viewSellerCategory() {
        List<SellerCategoryResponseDTO> responseList = categoryService.viewSellerCategory();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
    @GetMapping(value = {"api/customer/category/customer", "api/customer/category/customer/{id}"})
    public ResponseEntity<Set<Category>> viewCustomerCategory(@PathVariable(required = false) Integer id) throws EcommerceException {
        Set<Category> responseList = categoryService.viewCustomerCategory(id);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

}
