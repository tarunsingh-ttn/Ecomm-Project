package com.TTN.Ecommerce.Services;

import com.TTN.Ecommerce.DTO.*;
import com.TTN.Ecommerce.DTO.Category.*;
import com.TTN.Ecommerce.Entities.Category;
import com.TTN.Ecommerce.Entities.CategoryMetaValue;
import com.TTN.Ecommerce.Entities.CategoryMetadata;
import com.TTN.Ecommerce.Entities.CategoryMetadataFieldKey;
import com.TTN.Ecommerce.Exception.EcommerceException;
import com.TTN.Ecommerce.Repositories.CategoryMetaDataRepository;
import com.TTN.Ecommerce.Repositories.CategoryMetaValueRepository;
import com.TTN.Ecommerce.Repositories.CategoryRepository;
import com.TTN.Ecommerce.utils.FilterDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMetaDataRepository categoryMetaDataRepository;

    @Autowired
    private CategoryMetaValueRepository categoryMetaValueRepository;

    public String addCategory(CreateCategory category) throws EcommerceException {

        Category newCategory = new Category();

//        List<String> result=newCategory.getAllChildren(categoryRepository.findById(19L).get(),List.of("hello"));
//        if(categoryRepository.findByName(category.getName()).isPresent()){
//            return "category exist";
//        }
//        if(result.contains(category.getName())){
//            return "category exist";
//        }
        newCategory.setName(category.getName());
        if (category.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(category.getParentId())
                    .orElseThrow(() -> new EcommerceException("No parent Category Found with this id", HttpStatus.NOT_FOUND));
            newCategory.setParent(parentCategory);
        }
        Long id = categoryRepository.save(newCategory).getCatId();
        return "Category added with id " + id;
    }

    public ViewCategory viewCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).get();
        ViewCategory viewCategory = new ViewCategory();
        viewCategory.setCatId(category.getCatId());
        viewCategory.setName(category.getName());

        viewCategory.setParent(category.getParent());
        Set<ChildCategoryDTO> childList = new HashSet<>();
        for (Category child : category.getChildren()) {
            ChildCategoryDTO childCategory = new ChildCategoryDTO();
            childCategory.setName(child.getName());
            childCategory.setId(child.getCatId());
            childList.add(childCategory);
        }
        viewCategory.setChildren(childList);
        return viewCategory;
    }

    public List<ViewCategory> viewAllCategories(Integer PageNo,Integer pageSize,String sortBy,String sortOrder ){
        Iterable<Category> categoryPage = categoryRepository.findAll();
        List<ViewCategory> requiredCategories = new ArrayList<>();
        for (Category category : categoryPage) {
            ViewCategory categoryResponseDTO = new ViewCategory();
            categoryResponseDTO.setCatId(category.getCatId());
            categoryResponseDTO.setName(category.getName());
            categoryResponseDTO.setParent(category.getParent());
            Set<ChildCategoryDTO> childList = new HashSet<>();
            for (Category child : category.getChildren()) {
                ChildCategoryDTO childCategoryDTO = new ChildCategoryDTO();
                childCategoryDTO.setId(child.getCatId());
                childCategoryDTO.setName(child.getName());
                childList.add(childCategoryDTO);
            }
            categoryResponseDTO.setChildren(childList);

            List<CategoryMetaValue> metadataList = categoryMetaValueRepository.findByCategory(category);

            List<MetadataResponseDTO> metaList = new ArrayList<>();
            for (CategoryMetaValue metadata : metadataList) {
                MetadataResponseDTO metadataResponseDTO = new MetadataResponseDTO();
                metadataResponseDTO.setMetadataId(metadata.getCategoryMetadataField().getMetaId());
                metadataResponseDTO.setFieldName(metadata.getCategoryMetadataField().getName());
                metadataResponseDTO.setPossibleValues(metadata.getValue());
                metaList.add(metadataResponseDTO);
            }

            categoryResponseDTO.setMetadataList(metaList);
            requiredCategories.add(categoryResponseDTO);
        }
        return requiredCategories;

    }

    public String updateCategoryName(CategoryUpdateDTO categoryUpdateDTO) throws EcommerceException {
        Category category = categoryRepository.findById(categoryUpdateDTO.getCatId()).orElseThrow(() -> new EcommerceException(
                "not found", HttpStatus.NOT_FOUND
        ));
        BeanUtils.copyProperties(categoryUpdateDTO, category, FilterDto.getNullPropertyNames(category));
        categoryRepository.save(category);
        return "Update category success";
    }

    public CreateMetaFieldResponse addMetaValues(CreateMetaFieldValue createMetaFieldValue) throws EcommerceException {


        Long categoryId = createMetaFieldValue.getCategoryId();
        Long metadataId = createMetaFieldValue.getMetaFieldId();


        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new EcommerceException(
                "invalid id", HttpStatus.BAD_REQUEST
        ));
        CategoryMetadata metaField = categoryMetaDataRepository.findById(metadataId).orElseThrow(() -> new EcommerceException(
                "InvalidId", HttpStatus.BAD_REQUEST
        ));


        CategoryMetaValue fieldValue = new CategoryMetaValue();
        fieldValue.setCategoryMetadataFieldKey(new CategoryMetadataFieldKey(category.getCatId(), metaField.getMetaId()));
        fieldValue.setCategory(category);
        fieldValue.setCategoryMetadataField(metaField);

        String newValues = "";

        CategoryMetadataFieldKey key = new CategoryMetadataFieldKey(categoryId, metadataId);

        Optional<CategoryMetaValue> object = categoryMetaValueRepository.findById(key);
        String originalValues = "";
        if (object.isPresent()) {
            originalValues = object.get().getValue();
        }

        if (originalValues != null) {
            newValues = originalValues;
        }

        Optional<List<String>> check = Optional.of(List.of(originalValues.split(",")));

        for (String value : createMetaFieldValue.getValues()) {

            if (check.isPresent() && check.get().contains(value)) {
                throw new EcommerceException("invalid id", HttpStatus.BAD_REQUEST);
            }

            newValues = newValues.concat(value + ",");
        }
        fieldValue.setValue(newValues);

        categoryMetaValueRepository.save(fieldValue);

        // create appropriate responseDTO
        CreateMetaFieldResponse metaFieldValueResponse = new CreateMetaFieldResponse();
        metaFieldValueResponse.setCategoryId(category.getCatId());
        metaFieldValueResponse.setMetaFieldId(metaField.getMetaId());
        metaFieldValueResponse.setValues(fieldValue.getValue());


        return metaFieldValueResponse;

    }


    public List<SellerCategoryResponseDTO> viewSellerCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        List<SellerCategoryResponseDTO> resultList = new ArrayList<>();
        for (Category category : categoryList) {
            if (category.getChildren().isEmpty()) {
                List<CategoryMetaValue> metadataList =
                        categoryMetaValueRepository.findByCategory(category);
                SellerCategoryResponseDTO sellerResponse = new SellerCategoryResponseDTO();
                sellerResponse.setId(category.getCatId());
                sellerResponse.setName(category.getName());
                sellerResponse.setParent(category.getParent());
                List<MetadataResponseDTO> metaList = new ArrayList<>();
                for (CategoryMetaValue metadata : metadataList) {
                    MetadataResponseDTO metadataResponseDTO = new MetadataResponseDTO();
                    metadataResponseDTO.setMetadataId(metadata.getCategoryMetadataField().getMetaId());
                    metadataResponseDTO.setFieldName(metadata.getCategoryMetadataField().getName());
                    metadataResponseDTO.setPossibleValues(metadata.getValue());
                    metaList.add(metadataResponseDTO);
                }
                sellerResponse.setMetadata(metaList);
                resultList.add(sellerResponse);
            }
        }
        return resultList;
    }


    public Set<Category> viewCustomerCategory(Integer id) throws EcommerceException {
        if (id != null) {
            Category category = categoryRepository.findById((long) id).orElseThrow(() -> new EcommerceException(
                    "Invalid Category", HttpStatus.NOT_FOUND));
            Set<Category> childList = category.getChildren();
            return childList;
        } else {
            List<Category> categoryList = categoryRepository.findAll();
            Set<Category> rootNodes = new HashSet<>();
            for (Category category : categoryList) {
                if (category.getParent() == null) {
                    rootNodes.add(category);
                }
            }
            return rootNodes;
        }
    }

}
