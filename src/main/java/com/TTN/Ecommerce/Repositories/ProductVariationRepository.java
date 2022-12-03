package com.TTN.Ecommerce.Repositories;


import com.TTN.Ecommerce.Entities.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariationRepository extends JpaRepository<ProductVariation,Long> {
}
