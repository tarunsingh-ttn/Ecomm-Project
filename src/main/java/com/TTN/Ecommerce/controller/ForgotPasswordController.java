
package com.TTN.Ecommerce.controller;



import com.TTN.Ecommerce.dto.EmailDTO;
import com.TTN.Ecommerce.dto.PasswordDTO;
import com.TTN.Ecommerce.exception.EcommerceException;
import com.TTN.Ecommerce.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @PostMapping("/user/reset_password")
    public ResponseEntity<String> forgetPassowordCreateToken(@RequestBody @Valid EmailDTO emailDTO) throws EcommerceException {
        String message=forgotPasswordService.reset_password(emailDTO.getEmail());
        return new ResponseEntity<>(message,HttpStatus.CREATED);

    }

    @PostMapping("/user/confirm_reset_password")
    public ResponseEntity<String> forgetPassowordConfirmToken(@RequestParam("token") String confirmationToken ,
                                                       @RequestBody @Valid PasswordDTO passwordDTO) throws EcommerceException {
        String message=forgotPasswordService.confirmToken(confirmationToken,passwordDTO);
        return new ResponseEntity<>(message,HttpStatus.CREATED);
    }

}

