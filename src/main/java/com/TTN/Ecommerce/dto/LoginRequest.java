package com.TTN.Ecommerce.DTO;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
public class LoginRequest {
    @Email(message = "enter a valid username")
    private String username;
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,15}",
            message = "Enter a valid password.")
    private String password;
}
