package com.TTN.Ecommerce.Controller;

import com.TTN.Ecommerce.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value ={"/image"},consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> addImage(@RequestPart("imageFile")MultipartFile image){
        String message=userService.uploadImage();
        return new ResponseEntity<>("hello", HttpStatus.CREATED);
    }

}
