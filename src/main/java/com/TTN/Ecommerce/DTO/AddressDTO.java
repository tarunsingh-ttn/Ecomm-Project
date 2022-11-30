package com.TTN.Ecommerce.DTO;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class AddressDTO {

    @NotEmpty(message = "{address.city.absent}")
    @Pattern(regexp = "(^[A-Za-z ]*$)", message = "{address.city.invalid}")
    @Size(min = 2, max = 30)
    private String city;

    @NotEmpty(message = "{address.state.absent}")
    @Pattern(regexp = "(^[A-Za-z ]*$)", message = "{address.state.invalid}")
    @Size(min = 2, max = 30)
    private String state;

    @NotEmpty(message = "{address.country.absent}")
    @Pattern(regexp = "(^[A-Za-z ]*$)", message = "{address.country.invalid}")
    @Size(min = 2, max = 30)
    private String country;

    @NotEmpty(message = "{address.addressLine.absent}")
    @Pattern(regexp = "(^[A-Za-z0-9/., -]*$)", message = "{address.addressLine.invalid}")
    @Size(min = 2, max = 30)
    private String addressLine;

    @NotEmpty(message = "{address.zipCode.absent}")
    @Pattern(regexp = "(^[0-9]*$)", message = "address.zipCode.invalid")
    @Size(min = 6, max = 6, message = "address.zipCode.invalid")
    private String zipCode;

    private String label;

}
