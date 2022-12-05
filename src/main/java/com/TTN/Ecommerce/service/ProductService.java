package com.TTN.Ecommerce.service;

import com.TTN.Ecommerce.dto.product.*;
import com.TTN.Ecommerce.entity.*;
import com.TTN.Ecommerce.exception.EcommerceException;
import com.TTN.Ecommerce.repositories.*;
import com.TTN.Ecommerce.utils.FilterDto;
import com.TTN.Ecommerce.utils.JsonConvertor;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ProductService {
    @Autowired
    CategoryMetaValueRepository metaValueRepository;
    @Autowired
    ImageService imageService;
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
        Seller seller = sellerRepository.findByUser(userRepository.findByEmail(email));
        if (!(product.getSeller().getSeller_id() == seller.getSeller_id())) {
            throw new EcommerceException("PRODUCT_NOT_FOUND", HttpStatus.NOT_FOUND);
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
        Seller seller = sellerRepository.findByUser(userRepository.findByEmail(email));
        if (!(product.getSeller().getSeller_id() == seller.getSeller_id())) {
            throw new EcommerceException("PRODUCT_NOT_FOUND", HttpStatus.NOT_FOUND);
        }
        productRepository.delete(product);
        return "Product deleted successfully";
    }

    public List<ViewProductDTO> displayAllProducts(String email) throws EcommerceException {
        List<Product> productList = productRepository.findBySeller(sellerRepository.findByUser(userRepository.findByEmail(email)));
        if (productList.isEmpty()) {
            throw new EcommerceException("PRODUCT_NOT_FOUND", HttpStatus.NOT_FOUND);
        }
        Seller seller = sellerRepository.findByUser(userRepository.findByEmail(email));
        List<ViewProductDTO> viewProductDTOList = new ArrayList<>();
        productList.forEach((product) -> {
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

        Product product = productRepository.findById(prodId).orElseThrow(() -> new EcommerceException("PRODUCT_NOT_FOUND", HttpStatus.NOT_FOUND));
        Seller seller = sellerRepository.findByUser(userRepository.findByEmail(name));
        if (!(product.getSeller().getSeller_id() == seller.getSeller_id())) {
            throw new EcommerceException("PRODUCT_NOT_FOUND", HttpStatus.NOT_FOUND);
        }
        UpdateProductDTO updatedProduct = new UpdateProductDTO();
        if (newProduct.equals(updatedProduct)) {
            return "Nothing to update";
        }
        if (newProduct != null) {
            if (productRepository.findExistingProduct(newProduct.getName(), seller.getSeller_id(), product.getBrand(), product.getCategory().getCatId()).isPresent()) {
                throw new EcommerceException("Product.product.Invalid", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        BeanUtils.copyProperties(newProduct, product, FilterDto.getNullPropertyNames(newProduct));
        productRepository.save(product);
        return "Product updated successfully";
    }

    public String activateProduct(Long prodId) throws EcommerceException {
        Product product = productRepository.findById(prodId).orElseThrow(() -> new EcommerceException("PRODUCT_NOT_FOUND", HttpStatus.NOT_FOUND));

        if (product.isActive()) {
            return "PRODUCT is already active";
        }
        product.setActive(true);
        String sellerEmail = product.getSeller().getUser().getEmail();
        emailSenderService.sendEmail(sellerEmail, "Product activated", "Hi Your Product" + product.getName() + "has been activated");
        productRepository.save(product);
        return "Product has been successfully activated";
    }

    public String deactivateProduct(Long prodId) throws EcommerceException {
        Product product = productRepository.findById(prodId).orElseThrow(() -> new EcommerceException("PRODUCT_NOT_FOUND", HttpStatus.NOT_FOUND));

        if (!product.isActive()) {
            return "PRODUCT is already de-active";
        }
        product.setActive(false);
        String sellerEmail = product.getSeller().getUser().getEmail();
        emailSenderService.sendEmail(sellerEmail, "Product de-activated", "Hi Your Product" + product.getName() + "has been de-activated");
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

    public String addVariation(CreateVariation variationData, MultipartFile primaryImage,MultipartFile[] secondaryImages) throws EcommerceException, JSONException {
        ProductVariation variation = new ProductVariation();
        Product product = productRepository.findById(variationData.getProdId()).orElseThrow(() -> new EcommerceException("PRODUCT_NOT_FOUND", HttpStatus.NOT_FOUND));
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
        List<CategoryMetaValue> metavalue = metaValueRepository.findByCategory(product.getCategory());
        Map<String, String> metaDataFields = new HashMap<>();
        metavalue.forEach((CategoryMetaValue) -> {
            metaDataFields.put(CategoryMetaValue.getCategoryMetadataField().getName(), CategoryMetaValue.getValue());
        });
        JSONObject metadata = new JSONObject();

        for (Map.Entry<String, String> entry : variationData.getMetadata().entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();
            if (metaDataFields.get(k) == null || !metaDataFields.get(k).contains(v))
                throw new EcommerceException("Service.Product.Invalid.Metadata", HttpStatus.BAD_REQUEST);
            metadata.put(k, v);
        }
        variation.setPrice(variationData.getPrice());
        variation.setProduct(product);
        variation.setJsonData(metadata);
        variation.setQuantity(variationData.getQuantity());
        variation.setIsActive(false);
       Long varId= variationRepository.save(variation).getId();
        imageService.storeProductImages(product.getProdId(),primaryImage);
        if(secondaryImages.length!=0){
            imageService.storeVariationImages(varId,5L,secondaryImages);
        }

        return "Success";
    }

    public ViewVariation getVariation(String email, Long varId) throws EcommerceException {
        ProductVariation variation = variationRepository.findById(varId).orElseThrow(() -> new EcommerceException("SERVICE.VARIATION.NOT.FOUND", HttpStatus.NOT_FOUND));
        Product product = variation.getProduct();
        Seller seller = sellerRepository.findByUser(userRepository.findByEmail(email));
        if (!(product.getSeller().getSeller_id() == seller.getSeller_id())) {
            throw new EcommerceException("PRODUCT_NOT_FOUND", HttpStatus.NOT_FOUND);
        }
        ViewVariation variationResponse = new ViewVariation();
        variationResponse.setPrice(variation.getPrice());
        variationResponse.setProduct(product);
        variationResponse.setQuantity(variation.getQuantity());
        ObjectMapper mapper = new ObjectMapper();

//        try {
//            Map<String, String> map = mapper.readValue(variation.getJsonData().toString(), Map.class);
//            variationResponse.setMetadata(map);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
        Map<String, String> map = new HashMap<>();
        JSONObject jsonObject = variation.getJsonData();//getString
        Iterator<String> keysItr = jsonObject.keys();
        variationResponse.setVarId(variation.getId());
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            String value = "";
            try {
                value = value + jsonObject.get(key);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            map.put(key, value);
        }
        try {
            variationResponse.setPrimaryImage(imageService.showPrimaryImage(product.getProdId()));
            variationResponse.setSecondaryImages(imageService.showSecondaryImages(product.getProdId(),varId));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        variationResponse.setMetadata(map);

        return variationResponse;
    }

    public List<ViewVariation> viewAllPartitions(Long prodId, String email) throws EcommerceException {
        Product product = productRepository.findById(prodId).orElseThrow(() -> new EcommerceException("PRODUCT_NOT_FOUND", HttpStatus.BAD_REQUEST));
        List<ProductVariation> variations = variationRepository.findByProduct(product);
        List<ViewVariation> allVariations = new ArrayList<>();
        variations.forEach((variation) -> {
                    ViewVariation viewVariation = new ViewVariation();

                    //setiign metadata
                    Map<String, String> map = new HashMap<>();
                    JSONObject jsonObject = variation.getJsonData();//getString
                    Iterator<String> keysItr = jsonObject.keys();
                    viewVariation.setVarId(variation.getId());
                    while (keysItr.hasNext()) {
                        String key = keysItr.next();
                        String value = "";
                        try {
                            value = value + jsonObject.get(key);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        map.put(key, value);
                    }
                    viewVariation.setMetadata(map);
                    viewVariation.setProduct(variation.getProduct());
                    viewVariation.setPrice(variation.getPrice());
                    viewVariation.setQuantity(variation.getQuantity());
                    viewVariation.setVarId(variation.getId());
                    allVariations.add(viewVariation);
                }

        );

        return allVariations;
    }

    public String updateVaraition(Long varId, Authentication authentication, UpdateVariation newVariation) {
        ProductVariation productVariation = variationRepository.findById(varId).orElseThrow();
        JSONObject newMetadata = new JSONObject();
        newVariation.getMetadata().forEach((key, value) -> {
            try {
                newMetadata.put(key, value);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
        BeanUtils.copyProperties(newVariation, productVariation, FilterDto.getNullPropertyNames(productVariation));
        productVariation.setJsonData(newMetadata);
        variationRepository.save(productVariation);
        return "update success";
    }

    public CustomerViewProduct viewProductAsCustomer(Long prodId) throws EcommerceException {
        CustomerViewProduct customerProduct = new CustomerViewProduct();
        Product product = productRepository.findById(prodId).orElseThrow(() -> new EcommerceException("PRODUCT_NOT_FOUND", HttpStatus.NOT_FOUND));
       /* if(product.isDeleted() || !product.isActive()){
            throw new EcommerceException("PRODUCT_NOT_FOUND",HttpStatus.OK);
        }
        List<ProductVariation> productVariations = variationRepository.findByProduct(product);
        if(productVariations.isEmpty()){
            throw new EcommerceException("PRODUCT_NOT_FOUND",HttpStatus.OK);
        }*/
        customerProduct.setBrand(product.getBrand());
        customerProduct.setDescription(product.getDescription());
        customerProduct.setName(product.getName());
        customerProduct.setIsActive(product.isActive());
        customerProduct.setIsCancellable(product.isCancellable());
        customerProduct.setProdId(product.getProdId());
        customerProduct.setCategory(product.getCategory());
        List<CustomerViewVariation> variationList = new ArrayList<>();
        variationRepository.findByProduct(product).forEach((productVariation) -> {
            CustomerViewVariation variation = new CustomerViewVariation();
            variation.setPrice(productVariation.getPrice());
            variation.setVarId(productVariation.getId());
            variation.setMetadata(JsonConvertor.JSONtoMap(productVariation.getJsonData()));
            variationList.add(variation);
        });
        customerProduct.setVariations(variationList);

        return customerProduct;
    }

    public List<CustomerViewProduct> viewAllProductAsCustomer(Long catId) throws EcommerceException {
        List<CustomerViewProduct> productList = new ArrayList<>();
        Category category = categoryRepository.findById(catId).orElseThrow(() -> new EcommerceException("CATEGORY_NOT_FOUND", HttpStatus.BAD_REQUEST));
        if (!category.getChildren().isEmpty()) {
            throw new EcommerceException("CATEGORY_NOT_VALID", HttpStatus.BAD_REQUEST);
        }
        List<Product> products = productRepository.findByCategory(category);
        if (products.isEmpty()) {
            throw new EcommerceException("NO_PRODUCTS_FOUND", HttpStatus.NOT_FOUND);
        }
        for (Product product : products) {
            CustomerViewProduct customerViewProduct = new CustomerViewProduct();
            customerViewProduct.setProdId(product.getProdId());
            customerViewProduct.setName(product.getName());
            customerViewProduct.setBrand(product.getBrand());
            customerViewProduct.setDescription(product.getDescription());
            customerViewProduct.setIsActive(product.isActive());
            customerViewProduct.setIsCancellable(product.isCancellable());
            customerViewProduct.setIsReturnable(product.isReturnable());
            customerViewProduct.setCategory(product.getCategory());
            List<CustomerViewVariation> variationList = new ArrayList<>();
            variationRepository.findByProduct(product).forEach((productVariation) -> {
                CustomerViewVariation variation = new CustomerViewVariation();
                variation.setPrice(productVariation.getPrice());
                variation.setVarId(productVariation.getId());
                variation.setMetadata(JsonConvertor.JSONtoMap(productVariation.getJsonData()));
                variationList.add(variation);
            });
            customerViewProduct.setVariations(variationList);
            productList.add(customerViewProduct);
        }

        return productList;
    }
}
