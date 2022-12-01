package com.TTN.Ecommerce.DTO;

import lombok.Data;

import javax.validation.constraints.Email;
@Data
public class EmailDTO {
    @Email(message = "{user.email.invalid}")
    private String email;
}
