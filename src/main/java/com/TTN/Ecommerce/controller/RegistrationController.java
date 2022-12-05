package com.TTN.Ecommerce.controller;

import com.TTN.Ecommerce.dto.customer.CustomerDTO;
import com.TTN.Ecommerce.dto.seller.SellerDTO;
import com.TTN.Ecommerce.entity.Customer;
import com.TTN.Ecommerce.entity.Seller;
import com.TTN.Ecommerce.exception.EcommerceException;
import com.TTN.Ecommerce.repositories.UserRepository;
import com.TTN.Ecommerce.repositories.VerificationTokenRepository;
import com.TTN.Ecommerce.service.CustomerService;

import com.TTN.Ecommerce.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class RegistrationController {



    @Autowired
    private CustomerService customerService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository ;


    @PostMapping(path = "/register", headers = "user-role=SELLER")
    public ResponseEntity<Seller> registerUser(@RequestBody @Valid SellerDTO sellerDTO) throws EcommerceException {
        Seller seller = sellerService.createSeller(sellerDTO);
        return new ResponseEntity<Seller>(seller, HttpStatus.CREATED);

    }

    @PostMapping(path = "/register", headers = "user-role=CUSTOMER")
    public ResponseEntity<Customer> registerUser(@RequestBody @Valid CustomerDTO customerDTO) throws EcommerceException {
        Customer customer = customerService.createCustomer(customerDTO);
        return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
    }




}
