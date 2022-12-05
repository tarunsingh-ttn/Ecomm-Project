package com.TTN.Ecommerce.Repositories;


import com.TTN.Ecommerce.Entities.CategoryMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryMetaDataRepository extends JpaRepository<CategoryMetadata,Long> {



    CategoryMetadata findByName(String name);
}
