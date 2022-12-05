package com.TTN.Ecommerce.controller;


import com.TTN.Ecommerce.dto.LoginRequest;
import com.TTN.Ecommerce.exception.EcommerceException;
import com.TTN.Ecommerce.repositories.UserRepository;
import com.TTN.Ecommerce.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController
public class loginController {
    @Autowired
    LogoutService authenticationService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/customer")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public String home() {
        return "hello customer";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminHome() {
        return "Admin home";
    }

    @GetMapping("/seller")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public String userHome() {
        return "seller home";
    }

    @PostMapping("/api/user/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws EcommerceException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("client", "secret");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        if (userRepository.findByEmail(loginRequest.getUsername()) == null) {
            throw new EcommerceException("User not found", HttpStatus.NOT_FOUND);
        }
        if (!userRepository.findByEmail(loginRequest.getUsername()).isIS_ACTIVE()) {
            throw new EcommerceException("Activate your account First", HttpStatus.FORBIDDEN);
        }
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("grant_type", "password");
        valueMap.add("username", loginRequest.getUsername());
        valueMap.add("password", loginRequest.getPassword());

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(valueMap, headers);

        return restTemplate.postForEntity("http://localhost:8080/oauth/token",
                requestEntity, Object.class);

    }

    @PostMapping(path = "/api/logout")
    public ResponseEntity<String> userLogout(HttpServletRequest request) {
        String response = authenticationService.userSignOut(request);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}


