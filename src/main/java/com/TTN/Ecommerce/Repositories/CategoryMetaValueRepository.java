package com.TTN.Ecommerce.Repositories;

import com.TTN.Ecommerce.Entities.Category;
import com.TTN.Ecommerce.Entities.CategoryMetaValue;
import com.TTN.Ecommerce.Entities.CategoryMetadataFieldKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CategoryMetaValueRepository extends JpaRepository<CategoryMetaValue, CategoryMetadataFieldKey> {
    List<CategoryMetaValue> findByCategory(Category category);
    Optional<CategoryMetaValue> findById(CategoryMetadataFieldKey key);

}
