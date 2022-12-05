package com.TTN.Ecommerce.repositories;

import com.TTN.Ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query(value = "Select * from category where parent_id is NULL",nativeQuery = true)
    Category findRootNode();

    Optional<Category> findByName(String name);
}
