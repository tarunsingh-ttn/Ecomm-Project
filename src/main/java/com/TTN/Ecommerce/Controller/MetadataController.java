package com.TTN.Ecommerce.Controller;

import com.TTN.Ecommerce.DTO.CreateMetaData;
import com.TTN.Ecommerce.Entities.CategoryMetadata;
import com.TTN.Ecommerce.Exception.EcommerceException;
import com.TTN.Ecommerce.Services.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController

public class MetadataController {
    @Autowired
    private MetaDataService metaDataService;

    @PostMapping("/api/admin/metadata/add")
    public ResponseEntity<String> createMetaDataField(@RequestBody CreateMetaData metaData) throws EcommerceException {
        String message = metaDataService.createMetaData(metaData.getName());
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("api/admin/metadata/view")
    public ResponseEntity<List<CategoryMetadata>> viewMetaData() throws EcommerceException {
        List<CategoryMetadata> metadata= metaDataService.getMetaData();
        return new ResponseEntity<>(metadata,HttpStatus.OK);
    }

}
