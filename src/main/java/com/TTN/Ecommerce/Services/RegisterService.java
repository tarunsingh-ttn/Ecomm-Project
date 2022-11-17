package com.TTN.Ecommerce.Services;

import com.TTN.Ecommerce.DTO.CustomerDTO;
import com.TTN.Ecommerce.DTO.SellerDTO;
import com.TTN.Ecommerce.Entities.*;
import com.TTN.Ecommerce.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegisterService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Seller createSeller(SellerDTO sellerDTO) {
        User user = new User();
        user.setFirstName(sellerDTO.getFirstName());
        user.setLastName(sellerDTO.getLastName());
        user.setMiddleName(sellerDTO.getMiddleName());
        user.setEmail(sellerDTO.getEmail());


        Role role = roleRepository.findRoleByAuthority("SELLER");
        user.setRole(role);

        Seller seller = new Seller();
        seller.setGst(sellerDTO.getGst());
        seller.setCompanyName(sellerDTO.getCompanyName());
        seller.setCompanyContact(sellerDTO.getCompanyContact());
        seller.setUser(user);


        sellerRepository.save(seller);
        userRepository.save(user);

        return seller;
    }

    public Customer createCustomer(CustomerDTO customerDTO) {
        return new Customer();
    }

    public List<User> findAllCustomers() {
        return List.of();
    }
}
