package com.TTN.Ecommerce.Repositories;

import com.TTN.Ecommerce.Entities.Category;
import com.TTN.Ecommerce.Entities.Product;
import com.TTN.Ecommerce.Entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = "SELECT * FROM product WHERE name=:name and cat_id=:catId and seller_id=:sellerId and brand=:brand" ,nativeQuery = true)
    Optional<Product> findExistingProduct(String name, Long sellerId, String brand,Long catId);

    List<Product> findBySeller(Seller seller);
}
