package com.TTN.Ecommerce.controller;


import com.TTN.Ecommerce.dto.EmailDTO;
import com.TTN.Ecommerce.exception.EcommerceException;
import com.TTN.Ecommerce.repositories.UserRepository;
import com.TTN.Ecommerce.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class VerficationTokenController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    VerificationTokenService verificationTokenService;
    @GetMapping(value ="/user/confirm-account")
    @PostMapping(value ="/user/confirm-account")
    public ResponseEntity<String> activateAccount(@RequestParam("token")String confirmationToken) throws EcommerceException {
        String message=verificationTokenService.verifyToken(confirmationToken);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
    @PostMapping(value="/user/resend_token")
    public ResponseEntity<String> regenerateToken(@RequestBody @Valid EmailDTO emailDTO) throws EcommerceException{
        String message=verificationTokenService.reCreateToken(emailDTO.getEmail());
        return new ResponseEntity<>(message,HttpStatus.CREATED);
    }

}
