package com.TTN.Ecommerce.Services;

import com.TTN.Ecommerce.DTO.ProductDTO.CreateProductDTO;
import com.TTN.Ecommerce.DTO.ProductDTO.UpdateProductDTO;
import com.TTN.Ecommerce.DTO.ProductDTO.ViewProductDTO;
import com.TTN.Ecommerce.Entities.Category;
import com.TTN.Ecommerce.Entities.Product;
import com.TTN.Ecommerce.Entities.Seller;
import com.TTN.Ecommerce.Exception.EcommerceException;
import com.TTN.Ecommerce.Repositories.CategoryRepository;
import com.TTN.Ecommerce.Repositories.ProductRepository;
import com.TTN.Ecommerce.Repositories.SellerRepository;
import com.TTN.Ecommerce.Repositories.UserRepository;
import com.TTN.Ecommerce.utils.FilterDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

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
        product.setIS_CANCELLABLE(createProductDTO.isIS_CANCELLABLE());
        product.setIS_RETURNABLE(createProductDTO.isIS_RETURNABLE());
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
        productDTO.setActive(product.isIS_ACTIVE());
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
            productDTO.setActive(product.isIS_ACTIVE());
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

        if(product.isIS_ACTIVE()){
            return "PRODUCT is already active";
        }
        product.setIS_ACTIVE(true);
        String sellerEmail=product.getSeller().getUser().getEmail();
        emailSenderService.sendEmail(sellerEmail,"Product activated","Hi Your Product"+product.getName() +"has been activated");
        productRepository.save(product);
        return "Product has been successfully activated";
    }
    public String deactivateProduct(Long prodId) throws EcommerceException {
        Product product=productRepository.findById(prodId).orElseThrow(()->new EcommerceException("PRODUCT_NOT_FOUND",HttpStatus.NOT_FOUND));

        if(!product.isIS_ACTIVE()){
            return "PRODUCT is already de-active";
        }
        product.setIS_ACTIVE(false);
        String sellerEmail=product.getSeller().getUser().getEmail();
        emailSenderService.sendEmail(sellerEmail,"Product de-activated","Hi Your Product"+product.getName() +"has been de-activated");
        productRepository.save(product);
        return "Product has been successfully de-activated";
    }
}
