package com.TTN.Ecommerce.Services;

import com.TTN.Ecommerce.DAO.CustomerResponse;
import com.TTN.Ecommerce.Entities.Address;
import com.TTN.Ecommerce.Entities.Customer;
import com.TTN.Ecommerce.Entities.User;
import com.TTN.Ecommerce.Exception.EcommerceException;
import com.TTN.Ecommerce.Repositories.AddressRepository;
import com.TTN.Ecommerce.Repositories.CustomerRepository;
import com.TTN.Ecommerce.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CustomerService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

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
        emailSenderService.sendEmail(user,"Registration Completed","Your ecommerce account has been activated!!");


        return "Account has been activated";
    }

    public String deactivateUser(Long userId) throws EcommerceException {
        User user = userRepository.findById(userId).orElseThrow(() -> new EcommerceException("Service.USER_NOT_FOUND", HttpStatus.NOT_FOUND));
        if(!user.isIS_ACTIVE()){
            return "User is already de-activated";
        }
        user.setIS_ACTIVE(false);
        userRepository.save(user);
        emailSenderService.sendEmail(user,"Account Deactivated","Your ecommerce account has been de-activated!!");
        return "Account de-activation successful";
    }

    public Set<Address> displayAllAddresses(String email){
        User user = userRepository.findByEmail(email);

        Customer customer=customerRepository.findByUser(user);
        Set<Address> customerAddress=  customer.getAddresses();
        return customerAddress;
    }
}
