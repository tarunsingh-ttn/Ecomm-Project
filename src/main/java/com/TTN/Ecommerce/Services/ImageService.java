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

    private final String FOLDER_PATH="/home/tarun/Documents/E-commerce/src/main/resources/images/";

    public String storeImage(MultipartFile File) throws IOException {
        String imagePath=FOLDER_PATH +"/"+ File.getOriginalFilename();
        Image image=new Image();
        image.setName(File.getOriginalFilename());
        image.setPath(imagePath);
        imageRepository.save(image);
        File.transferTo(new File(imagePath));
        return "File saved to";
    }
    public byte[] showImage(String name) throws IOException {
        Optional<Image> image=imageRepository.findByName(name);
        String imagePath=image.get().getPath();
        byte[] images= Files.readAllBytes(new File(imagePath).toPath());
        return images;
    }


}
