package com.TTN.Ecommerce.repositories;


import com.TTN.Ecommerce.entity.CategoryMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryMetaDataRepository extends JpaRepository<CategoryMetadata,Long> {



    CategoryMetadata findByName(String name);
}
