package com.TTN.Ecommerce.Services;


import com.TTN.Ecommerce.Entities.User;
import com.TTN.Ecommerce.Entities.VerificationToken;
import com.TTN.Ecommerce.Exception.EcommerceException;
import com.TTN.Ecommerce.Repositories.UserRepository;
import com.TTN.Ecommerce.Repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class VerificationTokenService {

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RegisterService registerService;

    public String verifyToken(String confirmationToken) {
        VerificationToken token = verificationTokenRepository.findByVerificationToken(confirmationToken);

        if(token==null){
//            throw new EcommerceException("Service.INVALID_TOKEN");
            return "invalid token";
        }else{
            Calendar calendar = Calendar.getInstance();
            if(token.getExpiryDate().getTime()-calendar.getTime().getTime()<=0 ){
                registerService.createVerificationToken(token.getUser());
                return "token expired, new token is sent to ur email id senpai";
            }
            User user = userRepository.findByEmail(token.getUser().getEmail());
            user.setIS_ACTIVE(true);
            userRepository.save(user);
            verificationTokenRepository.delete(token);
            return "verifiaction successful";
        }

    }
}
