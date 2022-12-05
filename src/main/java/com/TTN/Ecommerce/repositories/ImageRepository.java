package com.TTN.Ecommerce.Repositories;

import com.TTN.Ecommerce.Entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long>{


    Image findByName(String name);

}
