package com.TTN.Ecommerce.Controller;


import com.TTN.Ecommerce.Entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

   /* @GetMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody){
        return new ResponseEntity<>("user authenticated", HttpStatus.OK);
    }*/

    @PostMapping(value="/api/user/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "User logged out";
    }






}


