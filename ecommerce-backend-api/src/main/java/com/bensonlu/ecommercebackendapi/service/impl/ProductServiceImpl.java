package com.bensonlu.ecommercebackendapi.service.impl;

import com.bensonlu.ecommercebackendapi.dto.ProductQueryParams;
import com.bensonlu.ecommercebackendapi.entity.Product;
import com.bensonlu.ecommercebackendapi.repository.ProductRepository;
import com.bensonlu.ecommercebackendapi.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final static Logger log= LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;
    @Override
    public Long countProduct(ProductQueryParams productQueryParams) {
        Specification<Product> spec = ProductSpecification.filterByParams(productQueryParams);
        return  productRepository.count(spec);
    }
    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        // Create a pageable object for pagination
        Pageable pageable = PageRequest.of(
                productQueryParams.getOffset()-1,
                productQueryParams.getLimit(),
                Sort.by("productId").descending()
        );

        // Create the specification for dynamic filtering
        Specification<Product> spec = ProductSpecification.filterByParams(productQueryParams);
        // Fetch the products using the specification
        Page<Product> productPage = productRepository.findAll(spec, pageable);

        // Return the page
        return productPage.getContent();
    }
    @Override
    public Product getProductById(Integer productId) {
        return productRepository.findByProductId(productId);
    }
    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product existingProductOpt = productRepository.findById(product.getProductId()).orElseThrow(() -> {
            log.warn("Product with ID {} not found ", product.getProductId());
            return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not registered");
        });
        if (product.getProductName() != null){
            existingProductOpt.setProductName(product.getProductName());
        }
        if (product.getCategory() != null){
            existingProductOpt.setCategory(product.getCategory());
        }
        if (product.getPrice() != null){
            existingProductOpt.setPrice(product.getPrice());
        }
        if (product.getStock() != null){
            existingProductOpt.setStock(product.getStock());
        }
        if (product.getDescription() != null){
            existingProductOpt.setDescription(product.getDescription());
        }
        if (product.getImageUrl() != null){
            existingProductOpt.setImageUrl(product.getImageUrl());
        }

        return productRepository.save(existingProductOpt);
    }

    public void deleteProductById(Integer productId) {
        productRepository.deleteById(productId);
    }
}
