package com.TTN.Ecommerce.Controller;


import com.TTN.Ecommerce.Entities.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class loginController {

    @GetMapping("/customer")

    public String home(){
        return "hello customer";
    }


    @GetMapping("/admin")
    public String adminHome(){
        return "Admin home";
    }
    @GetMapping("/user")
    public String userHome(){
        return "User home";
    }

    @GetMapping("/code")
    public String authCode(){return "Authorized";}




}


