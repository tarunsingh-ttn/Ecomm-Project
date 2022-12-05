package com.TTN.Ecommerce.repositories;


import com.TTN.Ecommerce.entity.Product;
import com.TTN.Ecommerce.entity.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductVariationRepository extends JpaRepository<ProductVariation,Long> {
    List<ProductVariation> findByProduct(Product product);
}
