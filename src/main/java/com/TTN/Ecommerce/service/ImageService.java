package com.TTN.Ecommerce.service;

import com.TTN.Ecommerce.entity.Image;
import com.TTN.Ecommerce.repositories.ImageRepository;
import com.TTN.Ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    private final String FOLDER_PATH = "/home/tarun/Documents/E-commerce/src/main/resources/images/users/";
    private final String PRODUCT_FOLDER_PATH = "/home/tarun/Documents/E-commerce/src/main/resources/images/product/";
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private UserRepository userRepository;

    public String storeImage(String email, MultipartFile File) throws IOException {
        Long id = userRepository.findByEmail(email).getUser_id();
        String name = id + ".jpg";
        String imagePath = FOLDER_PATH + name;
        File.transferTo(new File(imagePath));
        return "File saved to " + imagePath;
    }

    public byte[] showImage(Long id) throws IOException {
        String imagePath = FOLDER_PATH + id + ".jpg";
        byte[] images = Files.readAllBytes(new File(imagePath).toPath());
        return images;
    }

    public String storeProductImages(Long prodId, MultipartFile File) {
        String dirPath = PRODUCT_FOLDER_PATH + prodId;
        String name = prodId + "/" + prodId + ".jpg";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String imagePath = PRODUCT_FOLDER_PATH + name;
        try {
            File.transferTo(new File(imagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "File saved to " + imagePath;
    }

    public byte[] showPrimaryImage(Long prodId) throws IOException {
        String imagePath = PRODUCT_FOLDER_PATH + prodId + "/" + prodId + ".jpg";
        byte[] images = Files.readAllBytes(new File(imagePath).toPath());
        return images;
    }

    public List<byte[]> showSecondaryImages(Long prodId, Long varId) {
        String dirPath = PRODUCT_FOLDER_PATH + prodId + "/variations/";
        List<byte[]> secondaryImages = new ArrayList<>();
        File folder = new File(dirPath);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String imagePath = dirPath + listOfFiles[i].getName();
            byte[] image = new byte[0];
            try {
                image = Files.readAllBytes(new File(imagePath).toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            secondaryImages.add(image);
        }
        return secondaryImages;
    }


    public String storeVariationImages(Long prodId, Long varId, MultipartFile[] Files) {

        String dirPath = PRODUCT_FOLDER_PATH + prodId + "/variations/";

        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        int count = 1;
        for (MultipartFile file : Files) {
            try {
                String name = varId + "_" + count + ".jpg";
                String imagePath = dirPath + name;
                file.transferTo(new File(imagePath));
                count++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "File saved ";
    }


}
