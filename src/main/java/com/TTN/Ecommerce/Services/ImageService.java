package com.TTN.Ecommerce.Services;

import com.TTN.Ecommerce.Entities.Image;
import com.TTN.Ecommerce.Repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    private final String FOLDER_PATH="/home/tarun/Documents/E-commerce/src/main/resources/images/users/";

    public String storeImage(String email,MultipartFile File) throws IOException {

        String imagePath=FOLDER_PATH + File.getOriginalFilename();
        Image image=new Image();
        String name=File.getOriginalFilename();
        String m=name.substring(0,name.lastIndexOf("."));
        image.setName(m);
        image.setPath(imagePath);
        String type=File.getContentType();


        imageRepository.save(image);
        File.transferTo(new File(imagePath));
        return "File saved to";
    }
    public byte[] showImage(String name) throws IOException {

        Image image=imageRepository.findByName(name);
        String imagePath=image.getPath();
        byte[] images= Files.readAllBytes(new File(imagePath).toPath());

        return images;
    }


}
