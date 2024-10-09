package com.bensonlu.ecommercebackendapi.service;

import com.bensonlu.ecommercebackendapi.dto.ProductQueryParams;
import com.bensonlu.ecommercebackendapi.model.Product;
import com.bensonlu.ecommercebackendapi.repository.ProductRepository;

import java.util.List;

public interface ProductService {
    Long countProduct(ProductQueryParams productQueryParams);
    List<Product> getProducts(ProductQueryParams productQueryParams);
    Product getProductById(Integer productId);
    Product createProduct(Product product);
    void deleteProductById(Integer productId);

}
