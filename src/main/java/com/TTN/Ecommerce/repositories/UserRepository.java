package com.TTN.Ecommerce.Repositories;

import com.TTN.Ecommerce.Entities.Customer;
import com.TTN.Ecommerce.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);


}
