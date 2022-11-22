package com.TTN.Ecommerce.Services;


import com.TTN.Ecommerce.DAO.CustomerResponse;
import com.TTN.Ecommerce.DAO.SellerResponse;
import com.TTN.Ecommerce.Entities.Customer;
import com.TTN.Ecommerce.Entities.Seller;
import com.TTN.Ecommerce.Entities.User;
import com.TTN.Ecommerce.Repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    public List<SellerResponse> getAllSellers(){
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
            sellerResponsesList.add(sellerResponse);
        });

        return sellerResponsesList;
    }


}
