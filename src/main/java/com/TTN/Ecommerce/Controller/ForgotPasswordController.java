
package com.TTN.Ecommerce.Controller;



import com.TTN.Ecommerce.DTO.EmailDTO;
import com.TTN.Ecommerce.DTO.PasswordDTO;
import com.TTN.Ecommerce.Entities.User;
import com.TTN.Ecommerce.Exception.EcommerceException;
import com.TTN.Ecommerce.Services.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@RestController
@RequestMapping("/api")
public class ForgotPasswordController {


    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @PostMapping("/user/reset_password")
    public ResponseEntity<String> forgetPassowordToken(@RequestBody @Valid EmailDTO emailDTO) throws EcommerceException {
        String message=forgotPasswordService.reset_password(emailDTO.getEmail());
        return new ResponseEntity<>(message,HttpStatus.CREATED);

    }

    @PostMapping("/user/confirm_reset_password")
    public ResponseEntity<String> forgetPassowordToken(@RequestParam("token") String confirmationToken ,
                                                       @RequestBody @Valid PasswordDTO passwordDTO){
        String message=forgotPasswordService.confirmToken(confirmationToken,passwordDTO);
        return new ResponseEntity<>(message,HttpStatus.CREATED);
    }

}

