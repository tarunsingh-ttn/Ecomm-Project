package com.TTN.Ecommerce.Repositories;

import com.TTN.Ecommerce.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query(value = "Select * from category where parent_id is NULL",nativeQuery = true)
    Category findParentNode();
}
