package com.TTN.Ecommerce.Services;

import com.TTN.Ecommerce.Entities.User;
import com.TTN.Ecommerce.Entities.VerificationToken;
import com.TTN.Ecommerce.Exception.EcommerceException;
import com.TTN.Ecommerce.Repositories.UserRepository;
import com.TTN.Ecommerce.Repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class VerificationTokenService {
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailSenderService emailSenderService;
    public void createVerificationToken(User user) {
        VerificationToken token = verificationTokenRepository.findByUser(user);
        if (token != null) {
            verificationTokenRepository.delete(token);
        }
        VerificationToken verificationToken = new VerificationToken(user);
        verificationTokenRepository.save(verificationToken);
        String text = "To confirm your account, please click here : "
                + "http://localhost:8080/api/user/confirm-account?token=" + verificationToken.getVerificationToken();
        emailSenderService.sendEmail(user, "Complete Registration", text);
    }

    public String verifyToken(String confirmationToken) {
        VerificationToken token = verificationTokenRepository.findByVerificationToken(confirmationToken);
        if (token == null) {
            return "invalid token";
        } else {
            User user = token.getUser();
            Calendar calendar = Calendar.getInstance();
            if (token.getExpiryDate().getTime() - calendar.getTime().getTime() <= 0) {
                verificationTokenRepository.delete(token);
                createVerificationToken(user);
                return "token expired, new token is sent to ur email id ";
            }
            user.setIS_ACTIVE(true);
            userRepository.save(user);
            verificationTokenRepository.delete(token);
            return "verification successful";
        }

    }

    public String reCreateToken(String email) throws EcommerceException {
        User newUser = userRepository.findByEmail(email);
        if (newUser == null)
            throw new EcommerceException("Service.USER_NOT_FOUND");
        else {
            createVerificationToken(newUser);
        }
        return "New Token Sent";
    }
}
