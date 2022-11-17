package com.TTN.Ecommerce.DTO;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CustomerDTO extends UserDTO{
    private long cust_id;
    private String contact;

    private List<AddressDTO> addresses =new ArrayList<>();
}
