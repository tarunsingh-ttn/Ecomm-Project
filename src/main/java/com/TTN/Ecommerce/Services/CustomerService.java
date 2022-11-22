package com.TTN.Ecommerce.Services;

import com.TTN.Ecommerce.DAO.CustomerResponse;
import com.TTN.Ecommerce.Entities.Customer;
import com.TTN.Ecommerce.Entities.User;
import com.TTN.Ecommerce.Exception.EcommerceException;
import com.TTN.Ecommerce.Repositories.CustomerRepository;
import com.TTN.Ecommerce.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerResponse> getAllCustomers() throws EcommerceException {
        List<Customer> customerList = customerRepository.findAll();
        if(customerList.isEmpty()){
            throw new EcommerceException("Service.CUSTOMER_NOT_FOUND",HttpStatus.NOT_FOUND);
        }
        List<CustomerResponse> customerResponsesList = new ArrayList<>();
        customerList.forEach((customer) -> {
            CustomerResponse customerResponse = new CustomerResponse();
            customerResponse.setContact(customer.getContact());
            User customerUser = customer.getUser();
            customerResponse.setUser_id(customerUser.getUser_id());
            customerResponse.setEmail(customerUser.getEmail());
            customerResponse.setFullName(customerUser.getFirstName() + " " + customerUser.getMiddleName() + " " + customerUser.getLastName());
            customerResponsesList.add(customerResponse);
        });

        return customerResponsesList;
    }

    public String activateUser(Long userId) throws EcommerceException {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new EcommerceException("Service.USER_NOT_FOUND", HttpStatus.BAD_REQUEST));
        if(user.isIS_ACTIVE()){
            return "User is already active";
        }
        user.setIS_ACTIVE(true);
        userRepository.save(user);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Registration Completed");
        mailMessage.setFrom("admin@ttn");
        mailMessage.setText("Your ecommerce account has been activated!!");

        emailSenderService.sendEmail(mailMessage);
        return "Account has been activated";
    }

    public String deactivateUser(Long userId) throws EcommerceException {
        User user = userRepository.findById(userId).orElseThrow(() -> new EcommerceException("Service.USER_NOT_FOUND", HttpStatus.NOT_FOUND));
        if(!user.isIS_ACTIVE()){
            return "User is already de-activated";
        }
        user.setIS_ACTIVE(false);
        userRepository.save(user);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Account Deactivated");
        mailMessage.setFrom("admin@ttn");
        mailMessage.setText("Your ecommerce account has been de-activated!!");

        emailSenderService.sendEmail(mailMessage);
        return "Account de-activation successful";
    }
}
