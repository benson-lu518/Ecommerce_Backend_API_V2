package com.bensonlu.ecommercebackendapi.service.impl;

import com.bensonlu.ecommercebackendapi.dto.ProductQueryParams;
import com.bensonlu.ecommercebackendapi.model.Product;
import com.bensonlu.ecommercebackendapi.repository.ProductRepository;
import com.bensonlu.ecommercebackendapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public Long countProduct() {
        return  productRepository.count();
    }
    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        // Create a pageable object for pagination
        Pageable pageable = PageRequest.of(
                productQueryParams.getOffset(),
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
    public void deleteProductById(Integer productId) {
        productRepository.deleteById(productId);
    }
}
