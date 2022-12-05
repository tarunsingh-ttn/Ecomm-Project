package com.TTN.Ecommerce.repositories;

import com.TTN.Ecommerce.entity.Seller;
import com.TTN.Ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Long> {
    Seller findByUser(User user);
}
