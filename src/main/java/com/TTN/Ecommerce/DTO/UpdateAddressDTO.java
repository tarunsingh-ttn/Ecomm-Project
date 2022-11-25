package com.TTN.Ecommerce.DTO;

import lombok.Data;

@Data
public class UpdateAddressDTO {

    private String city;
    private String state;
    private String country;
    private String addressLine;
    private long zipCode;
    private String label;
}
