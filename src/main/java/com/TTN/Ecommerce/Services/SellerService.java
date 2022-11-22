package com.TTN.Ecommerce.Services;


import com.TTN.Ecommerce.DAO.SellerProfile;
import com.TTN.Ecommerce.DAO.SellerResponse;
import com.TTN.Ecommerce.Entities.Address;
import com.TTN.Ecommerce.Entities.Seller;
import com.TTN.Ecommerce.Entities.User;
import com.TTN.Ecommerce.Exception.EcommerceException;
import com.TTN.Ecommerce.Repositories.AddressRepository;
import com.TTN.Ecommerce.Repositories.SellerRepository;
import com.TTN.Ecommerce.Repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UserRepository userRepository;

    public List<SellerResponse> getAllSellers() throws EcommerceException {
        List<Seller> sellerList=sellerRepository.findAll();
        if(sellerList.isEmpty()){
            throw new EcommerceException("Service.NO_SELLER_FOUND", HttpStatus.NOT_FOUND);
        }

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
}
