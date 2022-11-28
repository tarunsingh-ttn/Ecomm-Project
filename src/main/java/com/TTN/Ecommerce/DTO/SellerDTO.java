package com.TTN.Ecommerce.DTO;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class SellerDTO extends UserDTO{


    @NotNull(message = "{seller.gst.absent}")
    @Pattern(regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$",
            message = "{seller.gst.invalid}")
    private String gst;

    @NotNull(message = "{seller.contact.absent}")
    @Size(min=10,max=10,message = "{seller.contact.invalid}")
    private String companyContact;

    @NotEmpty(message = "{seller.companyName.absent}")
    @Size(max=30, message = "{seller.companyName.absent}")
    private String companyName;
    @Valid
    private AddressDTO address;
}
