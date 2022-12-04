package com.TTN.Ecommerce.Repositories;


import com.TTN.Ecommerce.Entities.Product;
import com.TTN.Ecommerce.Entities.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductVariationRepository extends JpaRepository<ProductVariation,Long> {
    List<ProductVariation> findByProduct(Product product);
}
