package com.TTN.Ecommerce.Repositories;

import com.TTN.Ecommerce.Entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image,Long>{

    Optional<Image> findByName(String name);
}
