package com.TTN.Ecommerce.Controller;


import com.TTN.Ecommerce.DTO.CustomerResponse;
import com.TTN.Ecommerce.DTO.SellerResponse;
import com.TTN.Ecommerce.Exception.EcommerceException;
import com.TTN.Ecommerce.Services.CustomerService;
import com.TTN.Ecommerce.Services.SellerService;
import com.TTN.Ecommerce.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {


    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @Autowired
    private SellerService sellerService;

    @GetMapping("/sellers")
    public ResponseEntity<List<SellerResponse>> retrieveAllSellers() throws EcommerceException {
        List<SellerResponse> customers =sellerService.getAllSellers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerResponse>> retrieveAllCustomers() throws EcommerceException {
        List<CustomerResponse> customers =customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PutMapping("/activate/user")
    public ResponseEntity<String> enableUser(@RequestParam("userId") Long userId) throws EcommerceException {
        String response=userService.activateUser(userId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @PutMapping("/de-activate/user")
    public ResponseEntity<String> disableUser(@RequestParam("userId") Long userId) throws EcommerceException {
        String response=userService.deactivateUser(userId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }



}
