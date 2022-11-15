package com.TTN.Ecommerce.Controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class loginController {

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('CUSTOMER)")
    public String home(){
        return "hello user";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminHome(){
        return "Admin home";
    }
}
