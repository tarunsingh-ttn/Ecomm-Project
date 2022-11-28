package com.TTN.Ecommerce.DTO;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserDTO {


    @Email(message = "{user.email.invalid}")
    private String email;
    @NotEmpty(message = "{user.name.firstName.absent}")
    @Size(min = 2, max = 30, message = "{user.name.firstName.invalid}")
    @Pattern(regexp="(^[A-Za-z]*$)",message = "{user.name.firstName.invalid}")
    private String firstName;
    @Pattern(regexp="(^[A-Za-z]*$).{0,30}",message = "{user.name.middleName.invalid}")
    private String middleName;
    @NotEmpty(message = "{user.name.lastName.absent}")
    @Size(min = 2, max = 30, message = "{user.name.lastName.invalid}")
    @Pattern(regexp="(^[A-Za-z]*$)",message = "{user.name.lastName.invalid}")
    private String lastName;
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,15}",
            message = "{user.password.invalid}")
    private String password;
    private boolean IS_DELETED;
    private boolean IS_ACTIVE;
    private boolean IS_EXPIRED;
    private boolean IS_LOCKED;
    private Integer INVALID_ATTEMPT_COUNT;
    private boolean PASSWORD_UPDATE_DATE;
}
