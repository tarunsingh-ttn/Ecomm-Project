package com.TTN.Ecommerce.Services;

import com.TTN.Ecommerce.DTO.CustomerDTO;
import com.TTN.Ecommerce.DTO.SellerDTO;
import com.TTN.Ecommerce.Entities.*;
import com.TTN.Ecommerce.Exception.EcommerceException;
import com.TTN.Ecommerce.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class RegisterService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    public Seller createSeller(SellerDTO sellerDTO) throws EcommerceException {


        if( userRepository.findByEmail(sellerDTO.getEmail()) !=null) {
            throw new EcommerceException("Service.SELLER_ALREADY_EXISTS");
        }
        User user = new User();
        user.setFirstName(sellerDTO.getFirstName());
        user.setLastName(sellerDTO.getLastName());
        user.setMiddleName(sellerDTO.getMiddleName());
        user.setEmail(sellerDTO.getEmail());
        user.setPassword(sellerDTO.getPassword());

        Role role = roleRepository.findRoleByAuthority("SELLER");
        user.setRole(role);

        Seller seller = new Seller();
        seller.setGst(sellerDTO.getGst());
        seller.setCompanyName(sellerDTO.getCompanyName());
        seller.setCompanyContact(sellerDTO.getCompanyContact());
        seller.setUser(user);

        Address address= new Address();
        address.setCity(sellerDTO.getAddress().getCity());
        address.setState(sellerDTO.getAddress().getState());
        address.setAddressLine(sellerDTO.getAddress().getAddressLine());
        address.setZipCode(sellerDTO.getAddress().getZipCode());
        address.setCountry(sellerDTO.getAddress().getCountry());
        address.setLabel(sellerDTO.getAddress().getLabel());

        address.setSeller(seller);

        addressRepository.save(address);
        sellerRepository.save(seller);
        userRepository.save(user);
        createVerificationToken(user);
        return seller;
    }

    public void createVerificationToken(User user){
        VerificationToken verificationToken=new VerificationToken(user);
        verificationTokenRepository.save(verificationToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("tarunTTN@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                +"http://localhost:8080/api/user/confirm-account?token="+verificationToken.getVerificationToken());

        emailSenderService.sendEmail(mailMessage);

    }
    public Customer createCustomer(CustomerDTO customerDTO) throws EcommerceException {

        if( userRepository.findByEmail(customerDTO.getEmail()) !=null) {
            throw new EcommerceException("Service.CUSTOMER_ALREADY_EXISTS");
        }
        User user = new User();
        user.setFirstName(customerDTO.getFirstName());
        user.setLastName(customerDTO.getLastName());
        user.setPassword(customerDTO.getPassword());

        user.setMiddleName(customerDTO.getMiddleName());
        user.setEmail(customerDTO.getEmail());


        Role role = roleRepository.findRoleByAuthority("CUSTOMER");
        user.setRole(role);

        Customer customer = new Customer();
        customer.setContact(customerDTO.getContact());
        customer.setUser(user);

        customerDTO.getAddresses().forEach(addressDTO -> {
            Address address= new Address();
            address.setCity(addressDTO.getCity());
            address.setState(addressDTO.getState());
            address.setAddressLine(addressDTO.getAddressLine());
            address.setZipCode(addressDTO.getZipCode());
            address.setCountry(addressDTO.getCountry());
            address.setLabel(addressDTO.getLabel());
            address.setCustomer(customer);

            addressRepository.save(address);

        });

        customerRepository.save(customer);
        userRepository.save(user);
        createVerificationToken(user);

        return customer;
    }





}
