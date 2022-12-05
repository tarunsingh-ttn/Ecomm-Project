package com.TTN.Ecommerce.dto.seller;




import com.TTN.Ecommerce.entity.Address;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class SellerProfile {
    private long userId;

    @Size(min = 2, max = 30, message = "{user.name.firstName.invalid}")
    @Pattern(regexp="(^[A-Za-z]*$)",message = "{user.name.firstName.invalid}")
    private String firstName;

    @Size(min = 2, max = 30, message = "{user.name.lastName.invalid}")
    @Pattern(regexp="(^[A-Za-z]*$)",message = "{user.name.lastName.invalid}")
    private String lastName;

    private boolean isActive;


    @Size(min=10,max=10,message = "{seller.contact.invalid}")
    private String companyContact;


    @Size(min=10,max=10,message = "{seller.contact.invalid}")
    private String companyName;

    @Pattern(regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$",
            message = "{seller.gst.invalid}")
    private String gst;
    @Valid
    private Address address;
    private byte[] image;

}
