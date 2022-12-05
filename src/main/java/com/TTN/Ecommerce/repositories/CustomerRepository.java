package com.TTN.Ecommerce.repositories;

import com.TTN.Ecommerce.entity.Customer;
import com.TTN.Ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {


    Customer  findByUser(User user);


}
