package com.TTN.Ecommerce.Services;

import com.TTN.Ecommerce.DTO.ProductDTO.*;
import com.TTN.Ecommerce.Entities.*;
import com.TTN.Ecommerce.Exception.EcommerceException;
import com.TTN.Ecommerce.Repositories.*;
import com.TTN.Ecommerce.utils.FilterDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVariationRepository variationRepository;
    @Autowired
    private Environment environment;

    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired CategoryMetaValueRepository metaValueRepository;

    @Autowired
    private SellerRepository sellerRepository;

    public String createProduct(String email, CreateProductDTO createProductDTO) throws EcommerceException {
        Product product = new Product();
        Category category = categoryRepository.findById(createProductDTO.getCatId()).orElseThrow(() -> new EcommerceException("No Category Found with given categryID", HttpStatus.NOT_FOUND));
        if (!category.getChildren().isEmpty()) {
            throw new EcommerceException("Product.category.Invalid", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Seller seller = sellerRepository.findByUser(userRepository.findByEmail(email));
        if (productRepository.findExistingProduct(createProductDTO.getName(), seller.getSeller_id(), createProductDTO.getBrand(), createProductDTO.getCatId()).isPresent()) {
            throw new EcommerceException("Product.product.Invalid", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        product.setCategory(category);
        product.setSeller(seller);
        product.setName(createProductDTO.getName());
        product.setBrand(createProductDTO.getBrand());
        product.setDescription(createProductDTO.getDescription());
        product.setCancellable(createProductDTO.getIS_CANCELLABLE());
        product.setReturnable(createProductDTO.getIS_RETURNABLE());
        Long prodId = productRepository.save(product).getProdId();
        emailSenderService.sendEmail(environment.getProperty("ADMIN.EMAIL"), "New Product is added", "Product Details" + product.toString());

        return "Product created successfully with" + prodId;
    }
    public ViewProductDTO viewProduct(String email, Long prodId) throws EcommerceException {
        Product product = productRepository.findById(prodId).orElseThrow(() -> new EcommerceException("PRODUCT_NOT_FOUND", HttpStatus.NOT_FOUND));
        Seller seller=sellerRepository.findByUser(userRepository.findByEmail(email));
        if(!(product.getSeller().getSeller_id()==seller.getSeller_id())){
            throw new EcommerceException("PRODUCT_NOT_FOUND",HttpStatus.NOT_FOUND);
        }
        ViewProductDTO productDTO = new ViewProductDTO();
        productDTO.setCategory(product.getCategory());
        productDTO.setName(product.getName());
        productDTO.setBrand(product.getBrand());
        productDTO.setDescription(product.getDescription());
        productDTO.setProdId(product.getProdId());
        productDTO.setIsActive(product.isActive());
        productDTO.setIsCancellable(product.isCancellable());
        productDTO.setIsReturnable(product.isReturnable());
        return productDTO;
    }
    public String removeProduct(String email, Long prodId) throws EcommerceException {
        Product product = productRepository.findById(prodId).orElseThrow(() -> new EcommerceException("PRODUCT_NOT_FOUND", HttpStatus.NOT_FOUND));
        Seller seller=sellerRepository.findByUser(userRepository.findByEmail(email));
        if(!(product.getSeller().getSeller_id()==seller.getSeller_id())){
            throw new EcommerceException("PRODUCT_NOT_FOUND",HttpStatus.NOT_FOUND);
        }
        productRepository.delete(product);
        return "Product deleted successfully";
    }
    public List<ViewProductDTO> displayAllProducts(String email) throws EcommerceException {
        List<Product> productList=productRepository.findBySeller(sellerRepository.findByUser(userRepository.findByEmail(email)));
        if(productList.isEmpty()){
            throw new EcommerceException("PRODUCT_NOT_FOUND", HttpStatus.NOT_FOUND);
        }
        Seller seller=sellerRepository.findByUser(userRepository.findByEmail(email));
        List<ViewProductDTO> viewProductDTOList=new ArrayList<>();
        productList.forEach((product)->{
            ViewProductDTO productDTO = new ViewProductDTO();
            productDTO.setCategory(product.getCategory());
            productDTO.setName(product.getName());
            productDTO.setBrand(product.getBrand());
            productDTO.setDescription(product.getDescription());
            productDTO.setProdId(product.getProdId());
            productDTO.setIsActive(product.isActive());
            productDTO.setIsCancellable(product.isCancellable());
            productDTO.setIsReturnable(product.isReturnable());
            viewProductDTOList.add(productDTO);
        });
        return viewProductDTOList;
    }
    public String updateProduct(String name, Long prodId, UpdateProductDTO newProduct) throws EcommerceException {

        Product product=productRepository.findById(prodId).orElseThrow(()-> new EcommerceException("PRODUCT_NOT_FOUND",HttpStatus.NOT_FOUND));
        Seller seller=sellerRepository.findByUser(userRepository.findByEmail(name));
        if(!(product.getSeller().getSeller_id()==seller.getSeller_id())){
            throw new EcommerceException("PRODUCT_NOT_FOUND",HttpStatus.NOT_FOUND);
        }
        UpdateProductDTO updatedProduct=new UpdateProductDTO();
        if(newProduct.equals(updatedProduct)){
            return "Nothing to update";
        }
        BeanUtils.copyProperties(newProduct,product, FilterDto.getNullPropertyNames(newProduct));
        productRepository.save(product);
        return "Product updated successfully";
    }
    public String activateProduct(Long prodId) throws EcommerceException {
        Product product=productRepository.findById(prodId).orElseThrow(()->new EcommerceException("PRODUCT_NOT_FOUND",HttpStatus.NOT_FOUND));

        if(product.isActive()){
            return "PRODUCT is already active";
        }
        product.setActive(true);
        String sellerEmail=product.getSeller().getUser().getEmail();
        emailSenderService.sendEmail(sellerEmail,"Product activated","Hi Your Product"+product.getName() +"has been activated");
        productRepository.save(product);
        return "Product has been successfully activated";
    }
    public String deactivateProduct(Long prodId) throws EcommerceException {
        Product product=productRepository.findById(prodId).orElseThrow(()->new EcommerceException("PRODUCT_NOT_FOUND",HttpStatus.NOT_FOUND));

        if(!product.isActive()){
            return "PRODUCT is already de-active";
        }
        product.setActive(false);
        String sellerEmail=product.getSeller().getUser().getEmail();
        emailSenderService.sendEmail(sellerEmail,"Product de-activated","Hi Your Product"+product.getName() +"has been de-activated");
        productRepository.save(product);
        return "Product has been successfully de-activated";
    }
    public ViewProductDTO adminViewProduct(Long prodId) throws EcommerceException {
        Product product = productRepository.findById(prodId).orElseThrow(() -> new EcommerceException("PRODUCT_NOT_FOUND", HttpStatus.NOT_FOUND));
        ViewProductDTO viewProductDTO = new ViewProductDTO();
        viewProductDTO.setProdId(product.getProdId());
        viewProductDTO.setName(product.getName());
        viewProductDTO.setBrand(product.getBrand());
        viewProductDTO.setDescription(product.getDescription());
        viewProductDTO.setIsActive(product.isActive());
        viewProductDTO.setIsReturnable(product.isReturnable());
        viewProductDTO.setIsCancellable(product.isCancellable());
        viewProductDTO.setCategory(product.getCategory());
        return viewProductDTO;
    }

    public String addVariation(CreateVariation variationData) throws EcommerceException, JSONException {
        ProductVariation variation=new ProductVariation();
        Product product=productRepository.findById(variationData.getProdId()).orElseThrow(()->new EcommerceException("PRODUCT_NOT_FOUND",HttpStatus.NOT_FOUND));
//        if(!product.isActive()){
//            throw new EcommerceException("PRODUCT_NOT_ACTIVE",HttpStatus.BAD_REQUEST);
//        }
//        if(product.isDeleted()){
//            throw new EcommerceException("PRODUCT_IS_DELETED",HttpStatus.BAD_REQUEST);
//        }

//        ObjectMapper mapper=new ObjectMapper();
//        try {
//            String json= mapper.writeValueAsString(variationData.getMetadata());
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
        List<CategoryMetaValue> metavalue=metaValueRepository.findByCategory(product.getCategory());
        Map<String,String> metaDataFields=new HashMap<>();
        metavalue.forEach((CategoryMetaValue)->{
            metaDataFields.put(CategoryMetaValue.getCategoryMetadataField().getName(),CategoryMetaValue.getValue());
        });
        JSONObject metadata=new JSONObject();

        for (Map.Entry<String, String> entry : variationData.getMetadata().entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();
            if (metaDataFields.get(k) == null || !metaDataFields.get(k).contains(v))
                throw new EcommerceException("Service.Product.Invalid.Metadata", HttpStatus.BAD_REQUEST);
            metadata.put(k,v);
        }
        variation.setPrice(variationData.getPrice());
        variation.setProduct(product);
        variation.setJsonData(metadata);
        variation.setQuantity(variationData.getQuantity());
        variationRepository.save(variation);
        return "Success";
    }

    public ViewVariation getVariation(String email,Long varId) throws EcommerceException {
        ProductVariation variation=variationRepository.findById(varId).orElseThrow(()->new EcommerceException("SERVICE.VARIATION.NOT.FOUND",HttpStatus.NOT_FOUND));
        Product product=variation.getProduct();
        Seller seller=sellerRepository.findByUser(userRepository.findByEmail(email));
        if(!(product.getSeller().getSeller_id()==seller.getSeller_id())){
            throw new EcommerceException("PRODUCT_NOT_FOUND",HttpStatus.NOT_FOUND);
        }
        ViewVariation variationResponse=new ViewVariation();
        variationResponse.setPrice(variation.getPrice());
        variationResponse.setProduct(product);
        variationResponse.setQuantity(variation.getQuantity());
        ObjectMapper mapper=new ObjectMapper();

//        try {
//            Map<String, String> map = mapper.readValue(variation.getJsonData().toString(), Map.class);
//            variationResponse.setMetadata(map);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
        Map<String, String> map=new HashMap<>();
        JSONObject jsonObject=variation.getJsonData();//getString
        Iterator<String> keysItr = jsonObject.keys();
        variationResponse.setVarId(variation.getId());

        while(keysItr.hasNext()) {
            String key = keysItr.next();
            String value="" ;
            try {
                value = value+ jsonObject.get(key);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            map.put(key, value);
        }
        variationResponse.setMetadata(map);

        return variationResponse;
    }
}
