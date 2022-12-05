package com.TTN.Ecommerce.dto;


import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class UpdatePassword {
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,15}",
            message = "Enter a valid password. Password must contain 8-15 characters " +
                    "with at least 1 lower case, 1 upper case, 1 special character, and 1 Number.")
    private  String password;
    private String confirmPassword;

}
