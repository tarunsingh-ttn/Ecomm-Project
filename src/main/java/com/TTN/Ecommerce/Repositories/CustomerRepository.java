package com.TTN.Ecommerce.Repositories;

import com.TTN.Ecommerce.Entities.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer,Integer> {

}
