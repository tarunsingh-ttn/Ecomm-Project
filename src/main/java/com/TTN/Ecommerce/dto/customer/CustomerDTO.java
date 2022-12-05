package com.TTN.Ecommerce.dto.CustomerDTO;


import com.TTN.Ecommerce.dto.Address.AddressDTO;
import com.TTN.Ecommerce.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CustomerDTO extends UserDTO {


    @NotEmpty(message = "{Customer.contact.absent}")
    @Pattern(regexp = "^\\d{10}$", message = "{Customer.contact.invalid}")
    private String contact;
    @Valid
    private List<AddressDTO>  addresses=new ArrayList<>();
}
