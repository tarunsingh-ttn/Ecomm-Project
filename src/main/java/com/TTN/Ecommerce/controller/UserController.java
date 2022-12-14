package com.TTN.Ecommerce.controller;

import com.TTN.Ecommerce.service.ImageService;
import com.TTN.Ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @PostMapping(value ={"/seller/image"})
    public ResponseEntity<String> addImage(Authentication authentication ,@RequestParam("imageFile") MultipartFile image) throws IOException {
        String message=imageService.storeImage(authentication.getName(),image);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
    @GetMapping(value ={"/user/image/{name}"})
    public ResponseEntity<?> showImage(@PathVariable Long name) throws IOException {
      byte[] imageData=imageService.showImage(name);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
    }

}
