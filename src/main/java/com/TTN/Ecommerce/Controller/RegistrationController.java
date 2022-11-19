package com.TTN.Ecommerce.Controller;

import com.TTN.Ecommerce.DTO.CustomerDTO;
import com.TTN.Ecommerce.DTO.SellerDTO;
import com.TTN.Ecommerce.Entities.Customer;
import com.TTN.Ecommerce.Entities.Seller;
import com.TTN.Ecommerce.Entities.User;
import com.TTN.Ecommerce.Entities.VerificationToken;
import com.TTN.Ecommerce.Exception.EcommerceException;
import com.TTN.Ecommerce.Repositories.UserRepository;
import com.TTN.Ecommerce.Repositories.VerificationTokenRepository;
import com.TTN.Ecommerce.Services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class RegistrationController {

    @Autowired
    RegisterService registrationService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    VerificationTokenRepository verificationTokenRepository ;


    @PostMapping(path = "/register", headers = "user-role=SELLER")
    public ResponseEntity<Seller> registerUser(@RequestBody SellerDTO sellerDTO) throws EcommerceException {
        Seller seller = registrationService.createSeller(sellerDTO);
        return new ResponseEntity<Seller>(seller, HttpStatus.CREATED);

    }

    @PostMapping(path = "/register", headers = "user-role=CUSTOMER")
    public ResponseEntity<Customer> registerUser(@RequestBody CustomerDTO customerDTO) throws EcommerceException {
        Customer customer = registrationService.createCustomer(customerDTO);
        return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
    }




}
