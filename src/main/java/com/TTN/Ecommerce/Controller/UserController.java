package com.TTN.Ecommerce.Controller;

import com.TTN.Ecommerce.Services.ImageService;
import com.TTN.Ecommerce.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.io.IOException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @PostMapping(value ={"/image"})
    public ResponseEntity<String> addImage(Authentication authentication ,@RequestParam("imageFile")MultipartFile image) throws IOException {
        String message=imageService.storeImage(authentication.getName(),image);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
    @GetMapping(value ={"/image/{name}"})
    public ResponseEntity<?> showImage(@PathVariable String name) throws IOException {
      byte[] imageData=imageService.showImage(name);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
    }

}
