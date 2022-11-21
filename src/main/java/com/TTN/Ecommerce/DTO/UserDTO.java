package com.TTN.Ecommerce.DTO;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserDTO {


    @Email(message = "Enter a valid email.")
    private String email;
    @NotEmpty(message = "First Name is mandatory.")
    @Size(min = 2, max = 30, message = "Must contain 2-30 characters.")
    @Pattern(regexp="(^[A-Za-z]*$)",message = "Invalid Input. " +
            "Name can only contain alphabets.")
    private String firstName;
    @Pattern(regexp="(^[A-Za-z]*$).{0,30}",message = "Invalid Input. " +
            "Middle name can only contain alphabets. Must contain 1-30 characters.")
    private String middleName;
    @NotEmpty(message = "Last Name is mandatory.")
    @Size(min = 2, max = 30, message = "Must contain 2-30 characters.")
    @Pattern(regexp="(^[A-Za-z]*$)",message = "Invalid Input. " +
            "Name can only contain alphabets. Must contain 2-30 characters.")
    private String lastName;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,15}",
            message = "Enter a valid password.")
    private String password;
    private boolean IS_DELETED;
    private boolean IS_ACTIVE;
    private boolean IS_EXPIRED;
    private boolean IS_LOCKED;
    private Integer INVALID_ATTEMPT_COUNT;

    private boolean PASSWORD_UPDATE_DATE;
}
