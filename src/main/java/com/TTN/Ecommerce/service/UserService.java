package com.TTN.Ecommerce.Services;


import com.TTN.Ecommerce.dto.UpdatePassword;
import com.TTN.Ecommerce.entity.User;
import com.TTN.Ecommerce.exception.EcommerceException;
import com.TTN.Ecommerce.repositories.AddressRepository;
import com.TTN.Ecommerce.repositories.CustomerRepository;
import com.TTN.Ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailSenderService emailSenderService;
    public String activateUser(Long userId) throws EcommerceException {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new EcommerceException("Service.USER_NOT_FOUND", HttpStatus.BAD_REQUEST));
        if(user.isIS_ACTIVE()){
            throw new EcommerceException("User is already activated",HttpStatus.BAD_REQUEST);
        }
        user.setIS_ACTIVE(true);
        userRepository.save(user);
        emailSenderService.sendEmail(user.getEmail(),"Registration Completed","Your ecommerce account has been activated!!");


        return "Account has been activated";
    }
    public String deactivateUser(Long userId) throws EcommerceException {
        User user = userRepository.findById(userId).orElseThrow(() -> new EcommerceException("Service.USER_NOT_FOUND", HttpStatus.NOT_FOUND));
        if(!user.isIS_ACTIVE()){
            throw new EcommerceException("User is already de-activated",HttpStatus.BAD_REQUEST);
        }
        user.setIS_ACTIVE(false);
        userRepository.save(user);
        emailSenderService.sendEmail(user.getEmail(),"Account Deactivated","Your ecommerce account has been de-activated!!");
        return "Account de-activation successful";
    }
    public String updatePassword(String email, UpdatePassword updatePassword) throws EcommerceException {
        User user=userRepository.findByEmail(email);
        String password=updatePassword.getPassword();
        String confirmPass=updatePassword.getConfirmPassword();
        if(!password.equals(confirmPass)){
            throw new EcommerceException("Password do not match",HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(updatePassword.getPassword()));
        userRepository.save(user);

        emailSenderService.sendEmail(user.getEmail(),"password has been changed","Hi passowrd for this account has been changed");

        return "Password updated successfully";
    }
    public String uploadImage() {

        return "Success" ;
    }
}
