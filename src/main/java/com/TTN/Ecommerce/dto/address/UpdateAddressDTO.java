package com.TTN.Ecommerce.dto.Address;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UpdateAddressDTO {
    @Pattern(regexp="(^[A-Za-z ]*$)",message = "{address.city.invalid}")
    @Size(min = 2,max = 30,message = "{address.city.invalid}")
    private String city;
    @Pattern(regexp="(^[A-Za-z ]*$)",message = "{address.state.invalid}")
    @Size(min = 2,max = 30)
    private String state;
    @Pattern(regexp="(^[A-Za-z ]*$)",message = "{address.country.invalid}")
    @Size(min = 2,max = 30,message="{address.country.invalid}")
    private String country;
    @Pattern(regexp="(^[A-Za-z0-9/., -]*$)",message = "{address.addressLine.invalid}")
    @Size(min = 2,max = 30,message = "{address.addressLine.invalid}")
    private String addressLine;
    @Pattern(regexp="(^[0-9]*$)",message = "{address.zipCode.invalid}")
    @Size(min = 6,max = 6,message = "{address.zipCode.invalid}")
    private String zipCode;

    private String label;
}
