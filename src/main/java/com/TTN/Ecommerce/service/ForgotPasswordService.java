package com.TTN.Ecommerce.service;


import com.TTN.Ecommerce.dto.PasswordDTO;
import com.TTN.Ecommerce.entity.User;
import com.TTN.Ecommerce.entity.VerificationToken;
import com.TTN.Ecommerce.exception.EcommerceException;
import com.TTN.Ecommerce.repositories.UserRepository;
import com.TTN.Ecommerce.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
public class ForgotPasswordService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    public String reset_password(String email) throws EcommerceException {
        User newUser=userRepository.findByEmail(email);

        if(newUser==null)
            throw new EcommerceException("Service.USER_NOT_FOUND",HttpStatus.NOT_FOUND  );
        else {
            if(!newUser.isIS_ACTIVE()){
                throw new EcommerceException("Account is not active",HttpStatus.NOT_FOUND);
            }
            newUser.setPassword(UUID.randomUUID().toString());
            verificationTokenService.createVerificationToken(newUser);
            return "Service.FORGET_PASSWORD_TOKEN SENT";
            }
        }

    public String confirmToken(String confirmationToken, PasswordDTO passwordDTO) throws EcommerceException {
        VerificationToken token = verificationTokenRepository.findByVerificationToken(confirmationToken);
        if (token == null) {
            throw new EcommerceException("Invalid token", HttpStatus.NOT_FOUND);
        } else {
            Calendar calendar = Calendar.getInstance();
            if (token.getExpiryDate().getTime() - calendar.getTime().getTime() <= 0) {
                verificationTokenRepository.delete(token);
                return "token expired ";
            }
        }
        if(passwordDTO.getPassword().equals(passwordDTO.getConfirmPassword())){
            User user=token.getUser();
            user.setPassword(passwordDTO.getPassword());
            userRepository.save(user);
            verificationTokenRepository.delete(token);
        }
        return "Password updated successfully";
    }
}
