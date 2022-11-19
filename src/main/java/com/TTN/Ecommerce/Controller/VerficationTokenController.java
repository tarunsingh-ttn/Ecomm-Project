package com.TTN.Ecommerce.Controller;


import com.TTN.Ecommerce.Entities.VerificationToken;
import com.TTN.Ecommerce.Exception.EcommerceException;
import com.TTN.Ecommerce.Repositories.UserRepository;
import com.TTN.Ecommerce.Repositories.VerificationTokenRepository;
import com.TTN.Ecommerce.Services.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> regenerateToken(@RequestBody String email) throws EcommerceException{

        String message=verificationTokenService.reCreateToken(email);
        return new ResponseEntity<>(message,HttpStatus.CREATED);
    }

}
