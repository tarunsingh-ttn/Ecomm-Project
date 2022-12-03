package com.TTN.Ecommerce.Controller;


import com.TTN.Ecommerce.DTO.Seller.SellerProfile;
import com.TTN.Ecommerce.DTO.UpdatePassword;
import com.TTN.Ecommerce.Exception.EcommerceException;
import com.TTN.Ecommerce.Services.SellerService;
import com.TTN.Ecommerce.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<SellerProfile> viewProfile(Authentication authentication) throws EcommerceException {
        SellerProfile seller=sellerService.viewSellerProfile(authentication.getName());
        return new ResponseEntity<>(seller, HttpStatus.OK);

    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateProfile(Authentication authentication, @RequestBody @Valid SellerProfile profile) throws EcommerceException {
        String message=sellerService.updateProfile(authentication.getName(),profile);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
   /* @PatchMapping("/update")
    public ResponseEntity<String> updateProfile(Authentication authentication, @RequestBody Map<String,Object> fields) throws EcommerceException {
        String message=sellerService.updateProfile(authentication.getName(),fields);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }*/
    @PatchMapping("/update/pwd")
    public ResponseEntity<String> updatePassword(Authentication authentication, @RequestBody @Valid  UpdatePassword updatePassword) throws EcommerceException {
        String message=userService.updatePassword(authentication.getName(),updatePassword);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }


}
