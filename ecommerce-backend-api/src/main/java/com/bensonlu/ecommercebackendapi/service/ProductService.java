package com.bensonlu.ecommercebackendapi.service;

import com.bensonlu.ecommercebackendapi.dto.ProductQueryParams;
import com.bensonlu.ecommercebackendapi.model.Product;
import java.util.List;

public interface ProductService {
    Long countProduct();
    List<Product> getProducts(ProductQueryParams productQueryParams);
    Product getProductById(Integer productId);
    Product createProduct(Product product);
    void deleteProductById(Integer productId);

}
