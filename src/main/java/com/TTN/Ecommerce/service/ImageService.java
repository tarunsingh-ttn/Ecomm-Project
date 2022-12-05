package com.TTN.Ecommerce.Services;

import com.TTN.Ecommerce.entity.Image;
import com.TTN.Ecommerce.repositories.ImageRepository;
import com.TTN.Ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserRepository userRepository;

    private final String FOLDER_PATH="/home/tarun/Documents/E-commerce/src/main/resources/images/users/";

    public String storeImage(String email,MultipartFile File) throws IOException {
        Long id=userRepository.findByEmail(email).getUser_id();
        Image image=new Image();
        String name= id+".jpg";
        image.setName(name);
        String imagePath=FOLDER_PATH + name;
        image.setPath(imagePath);
        imageRepository.save(image);
        File.transferTo(new File(imagePath));
        return "File saved to "+imagePath;
    }
    public byte[] showImage(Long id) throws IOException {
        String imageName=id+".jpg";
        Image image=imageRepository.findByName(imageName);
        String imagePath=image.getPath();
        byte[] images= Files.readAllBytes(new File(imagePath).toPath());
        return images;
    }


}
