package com.TTN.Ecommerce.DTO;

import lombok.Data;

import javax.validation.constraints.Email;
@Data
public class EmailDTO {
    @Email
    private String email;
}
