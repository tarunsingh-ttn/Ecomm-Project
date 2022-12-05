package com.TTN.Ecommerce.dto.seller;


import com.TTN.Ecommerce.entity.Address;
import lombok.Data;

@Data
public class SellerResponse {


    private long user_id;
    private String fullName;
    private String email;
    private String gst;
    private String companyContact;
    private String companyName;
    private boolean IS_ACTIVE;
    private Address address;
    private byte[] sellerImage;
}
