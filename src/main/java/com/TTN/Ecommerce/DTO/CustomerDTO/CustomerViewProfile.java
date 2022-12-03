package com.TTN.Ecommerce.DTO.CustomerDTO;

import com.TTN.Ecommerce.Entities.Address;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class CustomerViewProfile {

        private long userId;
        private String firstName;
        private String lastName;
        private boolean isActive;
        private String Contact;
        private Set<Address> address;
        private byte[] image;

}
