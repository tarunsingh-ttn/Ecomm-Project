package com.TTN.Ecommerce.Controller;

import com.TTN.Ecommerce.DTO.ProductDTO.CreateProductDTO;
import com.TTN.Ecommerce.DTO.ProductDTO.UpdateProductDTO;
import com.TTN.Ecommerce.DTO.ProductDTO.ViewProductDTO;
import com.TTN.Ecommerce.Exception.EcommerceException;
import com.TTN.Ecommerce.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/api/seller/product/add")
    public ResponseEntity<String> addProduct(Authentication authentication, @RequestBody CreateProductDTO createProductDTO) throws EcommerceException {
        String message=productService.createProduct(authentication.getName(),createProductDTO);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/api/seller/product/view")
    public ResponseEntity<ViewProductDTO> viewProduct(Authentication authentication, @RequestParam Long prodId) throws EcommerceException {
        ViewProductDTO product=productService.viewProduct(authentication.getName(),prodId);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }
    @GetMapping("/api/seller/product/view/all")
    public ResponseEntity<List<ViewProductDTO>> viewAllProducts(Authentication authentication) throws EcommerceException{
        List<ViewProductDTO> productList=productService.displayAllProducts(authentication.getName());
        return new ResponseEntity<>(productList,HttpStatus.OK);
    }

    @DeleteMapping("api/seller/product/delete")
    public ResponseEntity<String> deleteProduct(Authentication authentication,@RequestParam Long prodId)throws EcommerceException{
        String message=productService.removeProduct(authentication.getName(),prodId);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
    @PatchMapping("api/seller/product/update")
    public ResponseEntity<String> updateProduct(Authentication authentication, @RequestParam Long prodId, @RequestBody UpdateProductDTO product)throws EcommerceException{
        String message=productService.updateProduct(authentication.getName(),prodId,product);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
    @PatchMapping("api/admin/product/activate")
    public ResponseEntity<String> activateProduct(@RequestParam Long prodId) throws EcommerceException {
        String message=productService.activateProduct(prodId);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
    @PatchMapping("api/admin/product/de-activate")
    public ResponseEntity<String> deactivateProduct(@RequestParam Long prodId) throws EcommerceException {
        String message=productService.deactivateProduct(prodId);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }



}
