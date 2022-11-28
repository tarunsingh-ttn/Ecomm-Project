package com.TTN.Ecommerce.Controller;


import com.TTN.Ecommerce.DTO.*;
import com.TTN.Ecommerce.Entities.Address;
import com.TTN.Ecommerce.Entities.Customer;
import com.TTN.Ecommerce.Exception.EcommerceException;
import com.TTN.Ecommerce.Services.CustomerService;
import com.TTN.Ecommerce.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    UserService userService;

    @Autowired
    CustomerService customerService;

    @GetMapping("addresses")
    public ResponseEntity<Set<Address>> viewAddresses(Authentication authentication){
        Set<Address> addresses=customerService.displayAllAddresses(authentication.getName());

    return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<CustomerViewProfile> viewProfile(Authentication authentication) throws EcommerceException {
        CustomerViewProfile customer =customerService.getProfile(authentication.getName());
        return new ResponseEntity<>(customer,HttpStatus.OK);
    }

    @PatchMapping("/edit/profile")
    public ResponseEntity<String> editProfile(Authentication authentication, @RequestBody CustomerUpdateProfile profile) throws EcommerceException {
        String message=customerService.editProfile(authentication.getName(),profile);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @PostMapping("/add/address")
    public ResponseEntity<String> addAddress(Authentication authentication ,@RequestBody UpdateAddressDTO addressDTO){
        String message=customerService.addAddress(authentication.getName(),addressDTO);
        return new ResponseEntity<>(message,HttpStatus.CREATED);
    }

    @PostMapping("/remove/address")
    public ResponseEntity<String> deleteAddress(Authentication authentication,@RequestParam long addressId) throws EcommerceException {
        String message=customerService.removeAddress(authentication.getName(),addressId);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @PatchMapping("/update/address")
    public ResponseEntity<String>  updateAddress(Authentication authentication,@RequestParam long addressId,@RequestBody @Valid UpdateAddressDTO address) throws EcommerceException {
        String message=customerService.updateAddress(authentication.getName(),addressId,address);
        return new ResponseEntity<>(message,HttpStatus.CREATED);
    }
}
