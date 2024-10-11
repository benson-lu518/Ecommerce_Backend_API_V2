package com.bensonlu.ecommercebackendapi.service;

import com.bensonlu.ecommercebackendapi.dto.ProductQueryParams;
import com.bensonlu.ecommercebackendapi.entity.Product;

import java.util.List;

public interface ProductService {
    Long countProduct(ProductQueryParams productQueryParams);
    List<Product> getProducts(ProductQueryParams productQueryParams);
    Product getProductById(Integer productId);
    Product createProduct(Product product);
    Product updateProduct(Product product);
    void deleteProductById(Integer productId);

}
