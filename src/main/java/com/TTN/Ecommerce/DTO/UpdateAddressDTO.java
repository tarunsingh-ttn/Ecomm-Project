package com.TTN.Ecommerce.DTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UpdateAddressDTO {

    private String city;
    private String state;
    private String country;
    private String addressLine;
    private long zipCode;
    private String label;
}
