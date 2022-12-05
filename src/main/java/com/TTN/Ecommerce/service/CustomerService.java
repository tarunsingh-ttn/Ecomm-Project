package com.TTN.Ecommerce.service;

import com.TTN.Ecommerce.dto.address.AddressDTO;
import com.TTN.Ecommerce.dto.address.UpdateAddressDTO;
import com.TTN.Ecommerce.dto.customer.CustomerDTO;
import com.TTN.Ecommerce.dto.customer.CustomerResponse;
import com.TTN.Ecommerce.dto.customer.CustomerUpdateProfile;
import com.TTN.Ecommerce.dto.customer.CustomerViewProfile;
import com.TTN.Ecommerce.entity.*;
import com.TTN.Ecommerce.exception.EcommerceException;
import com.TTN.Ecommerce.repositories.AddressRepository;
import com.TTN.Ecommerce.repositories.CustomerRepository;
import com.TTN.Ecommerce.repositories.RoleRepository;
import com.TTN.Ecommerce.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomerService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private VerificationTokenService verificationTokenService;
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
            try {
                customerResponse.setImage(imageService.showImage(customerUser.getUser_id()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            customerResponsesList.add(customerResponse);
        });

        return customerResponsesList;
    }
    public Customer createCustomer(CustomerDTO customerDTO) throws EcommerceException {
        if( userRepository.findByEmail(customerDTO.getEmail()) !=null) {
            throw new EcommerceException("Service.CUSTOMER_ALREADY_EXISTS",HttpStatus.CONFLICT);
        }
        User user = new User();
        user.setFirstName(customerDTO.getFirstName());
        user.setLastName(customerDTO.getLastName());
        user.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        user.setMiddleName(customerDTO.getMiddleName());
        user.setEmail(customerDTO.getEmail());
        Role role = roleRepository.findRoleByAuthority("CUSTOMER");
        user.setRole(role);
        Customer customer = new Customer();
        customer.setContact(customerDTO.getContact());
        customer.setUser(user);
        List<AddressDTO> addressList=customerDTO.getAddresses();
        customerRepository.save(customer);
        addressList.forEach(addressDTO -> {
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


        userRepository.save(user);
        verificationTokenService.createVerificationToken(user);

        return customer;
    }
    public Set<Address> displayAllAddresses(String email){
        User user = userRepository.findByEmail(email);
        Customer customer=customerRepository.findByUser(user);
        Set<Address> customerAddress=  customer.getAddresses();
        return customerAddress;
    }
    public CustomerViewProfile getProfile(String email) throws EcommerceException {
        User user=userRepository.findByEmail(email);
        if(user==null){
            throw new EcommerceException("Customer Not Found",HttpStatus.NOT_FOUND);
        }
        Customer customer=customerRepository.findByUser(user);
        CustomerViewProfile customerViewProfile=new CustomerViewProfile();
        customerViewProfile.setActive(user.isIS_ACTIVE());
        customerViewProfile.setAddress(customer.getAddresses());
        customerViewProfile.setContact(customer.getContact());
        customerViewProfile.setUserId(user.getUser_id());
        customerViewProfile.setFirstName(user.getFirstName());
        customerViewProfile.setLastName(user.getLastName());
        try {
            customerViewProfile.setImage(imageService.showImage(user.getUser_id()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return customerViewProfile;
    }
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
    public String editProfile(String email, CustomerUpdateProfile customerUpdateProfile) throws EcommerceException {
        User user=userRepository.findByEmail(email);
        Customer customer=customerRepository.findByUser(user);
        Set<Address> address=customer.getAddresses();
        BeanUtils.copyProperties(customerUpdateProfile, customer, getNullPropertyNames(customerUpdateProfile));
        BeanUtils.copyProperties(customerUpdateProfile,user,getNullPropertyNames(customerUpdateProfile));
        userRepository.save(user);
        customerRepository.save(customer);
        return "Profile edited Success";
    }
    public String addAddress(String email, UpdateAddressDTO addressDTO) {
        User user=userRepository.findByEmail(email);
        Customer customer=customerRepository.findByUser(user);
        Set<Address> addresses = customer.getAddresses();
        Address address=new Address();
        address.setAddressLine(addressDTO.getAddressLine());
        address.setCity(addressDTO.getCity());
        address.setZipCode(addressDTO.getZipCode());
        address.setLabel(addressDTO.getLabel());
        address.setState(addressDTO.getState());
        address.setCountry(addressDTO.getCountry());
        address.setCustomer(customer);
        addresses.add(address);
        addressRepository.save(address);

        return "Address Updated successfully";
    }
    public String removeAddress(String email, long addressId) throws EcommerceException {
        Address address=addressRepository.findById(addressId).orElseThrow(()-> new EcommerceException("Address Not found",HttpStatus.NOT_FOUND));
        User user=userRepository.findByEmail(email);
        Customer customer=customerRepository.findByUser(user);
        Long custid=address.getCustomer().getCust_id();
        if(customer.getCust_id()!=custid){
            throw  new EcommerceException("Address not found",HttpStatus.NOT_FOUND);
        }
        addressRepository.delete(address);
        return "Address deleted succesfully";
    }
    public String updateAddress(String email, long addressId, UpdateAddressDTO newAddress) throws EcommerceException {
        Address address=addressRepository.findById(addressId).orElseThrow(()-> new EcommerceException("Address Not found",HttpStatus.NOT_FOUND));
        User user=userRepository.findByEmail(email);
        Customer customer=customerRepository.findByUser(user);
        if(address.getCustomer()==null){
            throw new EcommerceException("Customer not found",HttpStatus.NOT_FOUND);
        }
        Long custid=address.getCustomer().getCust_id();

        if(customer.getCust_id()!=custid){
            throw  new EcommerceException("Address not found",HttpStatus.NOT_FOUND);
        }
        BeanUtils.copyProperties(newAddress,address,getNullPropertyNames(newAddress));
        addressRepository.save(address);
        return "Address Update successfully";
    }
}
