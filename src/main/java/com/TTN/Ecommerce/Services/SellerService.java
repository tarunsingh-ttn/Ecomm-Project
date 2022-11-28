package com.TTN.Ecommerce.Services;


import com.TTN.Ecommerce.DTO.SellerProfile;
import com.TTN.Ecommerce.DTO.SellerResponse;
import com.TTN.Ecommerce.DTO.SellerDTO;
import com.TTN.Ecommerce.Entities.Address;
import com.TTN.Ecommerce.Entities.Role;
import com.TTN.Ecommerce.Entities.Seller;
import com.TTN.Ecommerce.Entities.User;
import com.TTN.Ecommerce.Exception.EcommerceException;
import com.TTN.Ecommerce.Repositories.AddressRepository;
import com.TTN.Ecommerce.Repositories.RoleRepository;
import com.TTN.Ecommerce.Repositories.SellerRepository;
import com.TTN.Ecommerce.Repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private ImageService imageService;

    public Seller createSeller(SellerDTO sellerDTO) throws EcommerceException {
        if( userRepository.findByEmail(sellerDTO.getEmail()) !=null) {
            throw new EcommerceException("Service.SELLER_ALREADY_EXISTS", HttpStatus.CONFLICT);
        }
        User user = new User();
        user.setFirstName(sellerDTO.getFirstName());
        user.setLastName(sellerDTO.getLastName());
        user.setMiddleName(sellerDTO.getMiddleName());
        user.setEmail(sellerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(sellerDTO.getPassword()));
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
        sellerRepository.save(seller);
        address.setSeller(seller);
        addressRepository.save(address);
        userRepository.save(user);
        verificationTokenService.createVerificationToken(user);
        return seller;
    }
    public List<SellerResponse> getAllSellers() throws EcommerceException {
        if(sellerRepository.findAll().isEmpty()){
            throw new EcommerceException("NO_SELLER_FOUND", HttpStatus.NOT_FOUND);
        }
        List<Seller> sellerList=sellerRepository.findAll();
        List<SellerResponse>sellerResponsesList=new ArrayList<>();
        sellerList.forEach((seller)->{
            SellerResponse sellerResponse=new SellerResponse();
            sellerResponse.setCompanyContact(seller.getCompanyContact());
            User user=seller.getUser();
            sellerResponse.setUser_id(user.getUser_id());
            sellerResponse.setEmail(user.getEmail());
            sellerResponse.setFullName(user.getFirstName()+ " " +user.getMiddleName()+ " "+user.getLastName());
            sellerResponse.setIS_ACTIVE(user.isIS_ACTIVE());
            sellerResponse.setGst(seller.getGst());
            sellerResponse.setAddress(seller.getAddress());
            try {
                sellerResponse.setSellerImage(imageService.showImage(user.getEmail()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sellerResponsesList.add(sellerResponse);
        });
        return sellerResponsesList;
    }
    public SellerProfile viewSellerProfile(String email) throws EcommerceException {
        User user=userRepository.findByEmail(email);
        if(user==null){
            throw new EcommerceException("User Not Found",HttpStatus.NOT_FOUND);
        }
        Seller seller=sellerRepository.findByUser(user);
        if(seller==null){
            throw new EcommerceException("Seller not Found",HttpStatus.NOT_FOUND);
        }
        SellerProfile sellerProfile=new SellerProfile();
        sellerProfile.setFirstName(user.getFirstName());
        sellerProfile.setLastName(user.getLastName());
        sellerProfile.setAddress(seller.getAddress());
        sellerProfile.setGst(seller.getGst());
        sellerProfile.setCompanyContact(seller.getCompanyContact());
        sellerProfile.setActive(user.isIS_ACTIVE());
        sellerProfile.setUserId(user.getUser_id());

        return sellerProfile;
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

    public String updateProfile(String email, SellerProfile profile) throws EcommerceException {
        User user=userRepository.findByEmail(email);
        SellerProfile sellerProfile=new SellerProfile();
        if(user==null){
            throw new EcommerceException("User Not Found",HttpStatus.NOT_FOUND);
        }
        Seller seller=sellerRepository.findByUser(user);
        if(seller==null){
            throw new EcommerceException("Seller not Found",HttpStatus.NOT_FOUND);
        }
        Address address=seller.getAddress();
        BeanUtils.copyProperties(profile, seller, getNullPropertyNames(profile));
        BeanUtils.copyProperties(profile,user,getNullPropertyNames(profile));
        BeanUtils.copyProperties(profile,address,getNullPropertyNames(profile));
        userRepository.save(user);
        sellerRepository.save(seller);
        return "success";
    }


/*    public String updateProfile(String email,Map<String,Object> fields) throws EcommerceException {
        User user=userRepository.findByEmail(email);
        SellerProfile sellerProfile=new SellerProfile();
        if(user==null){
            throw new EcommerceException("User Not Found",HttpStatus.NOT_FOUND);
        }

        Seller seller=sellerRepository.findByUser(user);
        if(seller==null){
            throw new EcommerceException("Seller not Found",HttpStatus.NOT_FOUND);
        }
        sellerProfile.setActive(user.isIS_ACTIVE());
        sellerProfile.setLastName(user.getLastName());
        sellerProfile.setFirstName(user.getFirstName());
        sellerProfile.setCompanyContact(seller.getCompanyContact());
        sellerProfile.setUserId(user.getUser_id());
        sellerProfile.setCompanyName(seller.getCompanyName());
        sellerProfile.setGst(seller.getGst());
        sellerProfile.setAddress(seller.getAddress());
        fields.forEach((keys,value)->{
            Field field=ReflectionUtils.findField(SellerProfile.class,(String) keys);
            field.setAccessible(true);
            ReflectionUtils.setField(field,sellerProfile,value);
        });
        user.setFirstName(sellerProfile.getFirstName());
        user.setLastName(sellerProfile.getLastName());
        seller.setAddress(sellerProfile.getAddress());
        seller.setCompanyName(sellerProfile.getCompanyName());
        seller.setGst(sellerProfile.getGst());
        userRepository.save(user);
        sellerRepository.save(seller);
        System.out.println(user);

        return "success";
    }*/


}
