package com.TTN.Ecommerce.DAO;




import com.TTN.Ecommerce.DTO.AddressDTO;
import com.TTN.Ecommerce.Entities.Address;
import lombok.Data;

@Data
public class SellerProfile {
    private long userId;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private String companyContact;
    private String companyName;
    private String gst;
    private Address address;

}
