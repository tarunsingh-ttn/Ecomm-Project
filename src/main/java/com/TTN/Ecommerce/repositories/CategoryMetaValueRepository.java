package com.TTN.Ecommerce.repositories;

import com.TTN.Ecommerce.entity.Category;
import com.TTN.Ecommerce.entity.CategoryMetaValue;
import com.TTN.Ecommerce.entity.CategoryMetadataFieldKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CategoryMetaValueRepository extends JpaRepository<CategoryMetaValue, CategoryMetadataFieldKey> {
    List<CategoryMetaValue> findByCategory(Category category);
    Optional<CategoryMetaValue> findById(CategoryMetadataFieldKey key);

}
