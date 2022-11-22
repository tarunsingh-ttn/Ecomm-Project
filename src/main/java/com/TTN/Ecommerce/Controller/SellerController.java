package com.TTN.Ecommerce.Controller;


import com.TTN.Ecommerce.DAO.SellerProfile;
import com.TTN.Ecommerce.Entities.Seller;
import com.TTN.Ecommerce.Exception.EcommerceException;
import com.TTN.Ecommerce.Services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @GetMapping("/profile")
    public ResponseEntity<SellerProfile> viewProfile(Authentication authentication) throws EcommerceException {

        SellerProfile seller=sellerService.viewSellerProfile(authentication.getName());
        return new ResponseEntity<>(seller, HttpStatus.OK);

    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateProfile(Authentication authentication, @RequestBody SellerProfile profile) throws EcommerceException {
        String message=sellerService.updateProfile(authentication.getName(),profile);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }


}
