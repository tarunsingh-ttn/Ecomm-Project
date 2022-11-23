package com.TTN.Ecommerce.Controller;


import com.TTN.Ecommerce.Entities.Address;
import com.TTN.Ecommerce.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("addresses")
    public ResponseEntity<Set<Address>> viewAddresses(Authentication authentication){
        Set<Address> addresses=customerService.displayAllAddresses(authentication.getName());

    return new ResponseEntity<>(addresses, HttpStatus.OK);
    }


}
