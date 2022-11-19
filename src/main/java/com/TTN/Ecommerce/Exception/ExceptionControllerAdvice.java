package com.TTN.Ecommerce.Exception;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @Autowired
    private Environment environment;

    @ExceptionHandler(EcommerceException.class)
    public ResponseEntity<String> ecommerceExceptionHandler(EcommerceException exception){
        return new ResponseEntity<>(environment.getProperty(exception.getMessage()), HttpStatus.CONFLICT);
    }

}
