package com.TTN.Ecommerce.Exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class EcommerceException extends Exception{
    private HttpStatus status = null;
    public EcommerceException(){
        super();
    }

    public EcommerceException(String message){
        super(message);
    }
    public EcommerceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
