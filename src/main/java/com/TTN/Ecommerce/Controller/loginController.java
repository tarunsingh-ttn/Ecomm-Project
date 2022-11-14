package com.TTN.Ecommerce.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class loginController {

    @GetMapping
    public String home(){
        return "hello user";
    }
}
