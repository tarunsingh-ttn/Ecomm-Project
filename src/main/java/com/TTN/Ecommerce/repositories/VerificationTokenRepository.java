package com.TTN.Ecommerce.repositories;

import com.TTN.Ecommerce.entity.User;
import com.TTN.Ecommerce.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Integer> {
    VerificationToken findByVerificationToken(String confirmationToken);
    VerificationToken findByUser(User user);
}
