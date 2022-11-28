package com.TTN.Ecommerce.DTO;


import com.TTN.Ecommerce.Entities.Address;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CustomerDTO extends UserDTO{


    @NotEmpty(message = "Cust Phone number is a mandatory field.")
    @Pattern(regexp = "^\\d{10}$", message = "Enter a valid ten-digit phone number.")
    private String contact;
    @Valid
    private List<AddressDTO>  addresses=new ArrayList<>();
}
