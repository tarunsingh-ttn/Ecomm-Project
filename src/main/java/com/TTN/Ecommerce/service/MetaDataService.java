package com.TTN.Ecommerce.service;


import com.TTN.Ecommerce.entity.CategoryMetadata;
import com.TTN.Ecommerce.exception.EcommerceException;
import com.TTN.Ecommerce.repositories.CategoryMetaDataRepository;
import com.TTN.Ecommerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetaDataService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMetaDataRepository metaDataRepository;

    public String createMetaData(String name) throws EcommerceException {
        String n=name.stripIndent();
        CategoryMetadata newMetadata=new CategoryMetadata();
        CategoryMetadata metadata=metaDataRepository.findByName(name);
        if(metadata!=null){
            throw new EcommerceException("API.METADATA.ALREADY.EXIST", HttpStatus.BAD_REQUEST);
        }
        newMetadata.setName(name);
        Long id= metaDataRepository.save(newMetadata).getMetaId();
        String response="MetaData created with I.D."+id;
        return response;
    }

    public List<CategoryMetadata> getMetaData() throws EcommerceException {
        List<CategoryMetadata> metadata=metaDataRepository.findAll();
        if(metadata.isEmpty()){
            throw new EcommerceException("No meta data found");
        }
        return metadata;
    }
}
