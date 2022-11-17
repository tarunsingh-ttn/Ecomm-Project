package com.TTN.Ecommerce.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO extends UserDTO{
    private long cust_id;
    private String contact;
}
