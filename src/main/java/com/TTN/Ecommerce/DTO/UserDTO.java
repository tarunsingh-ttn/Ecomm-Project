package com.TTN.Ecommerce.DTO;


import lombok.Data;

@Data
public class UserDTO {

    private long user_id;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private boolean IS_DELETED;
    private boolean IS_ACTIVE;
    private boolean IS_EXPIRED;
    private boolean IS_LOCKED;
    private boolean INVALID_ATTEMPT_COUNT;
    private boolean PASSWORD_UPDATE_DATE;
}
