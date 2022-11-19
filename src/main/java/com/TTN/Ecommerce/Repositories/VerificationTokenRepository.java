package com.TTN.Ecommerce.Repositories;

import com.TTN.Ecommerce.Entities.User;
import com.TTN.Ecommerce.Entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Integer> {
    VerificationToken findByVerificationToken(String confirmationToken);
    VerificationToken findByUser(User user);
}
