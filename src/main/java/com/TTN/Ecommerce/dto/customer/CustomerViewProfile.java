package com.TTN.Ecommerce.dto.CustomerDTO;

import com.TTN.Ecommerce.entity.Address;
import lombok.Data;

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
