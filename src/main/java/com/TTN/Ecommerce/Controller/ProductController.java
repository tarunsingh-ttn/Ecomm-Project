package com.TTN.Ecommerce.Controller;

import com.TTN.Ecommerce.DTO.ProductDTO.*;
import com.TTN.Ecommerce.Entities.ProductVariation;
import com.TTN.Ecommerce.Exception.EcommerceException;
import com.TTN.Ecommerce.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
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

    //not tested
    @GetMapping("api/admin/product/view")
    public ResponseEntity<ViewProductDTO> viewAdminProduct(@PathVariable Long prodId) throws EcommerceException {
        ViewProductDTO response = productService.adminViewProduct(prodId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


//    @GetMapping("api/admin/product/view/al")
//    public ResponseEntity<List<ProductResponseDTO>> viewAdminAllProducts(){
//        List<ProductResponseDTO> response = productService.adminViewAllProducts();
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @PostMapping("api/seller/variant/add")
    public ResponseEntity<String> createVariation(@RequestBody CreateVariation variation) throws EcommerceException, JSONException {
        String message=productService.addVariation(variation);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
    @GetMapping("api/seller/variant/view")
    public ResponseEntity<ViewVariation> viewVariation(Authentication authentication,@RequestParam Long varId) throws EcommerceException {
        return new ResponseEntity<>(productService.getVariation(authentication.getName(),varId),HttpStatus.OK);
    }



}
