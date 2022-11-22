package com.TTN.Ecommerce.DAO;


import lombok.Data;

@Data
public class CustomerResponse {
    private long user_id;
    private String email;
    private String fullName;
    private String contact;
    private boolean IS_ACTIVE;
}
