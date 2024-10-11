package com.bensonlu.ecommercebackendapi.controller;

import com.bensonlu.ecommercebackendapi.constant.ProductCategory;
import com.bensonlu.ecommercebackendapi.dto.ProductQueryParams;
import com.bensonlu.ecommercebackendapi.entity.Product;
import com.bensonlu.ecommercebackendapi.service.ProductService;
import com.bensonlu.ecommercebackendapi.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    // Query all products
    @GetMapping("/products")
    public ResponseEntity<Page<Product>>getProducts(
            //conditional filtering
            @RequestParam(required = false) ProductCategory category,// FOOD,CAR, E_BOOK
            @RequestParam(required = false) String search, // search for product name
            //sorting type
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort, //descending or ascending
            //paging
            @RequestParam(defaultValue = "10") @Max(100) @Min(0) Integer limit,
            @RequestParam(defaultValue = "1") @Min(1) Integer offset // page number
            ){
        //set the value to the class
        ProductQueryParams productQueryParams=new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        //get product list
        List<Product> productList=productService.getProducts(productQueryParams);
        //get total products num
        Long total=productService.countProduct(productQueryParams);

        //the class to present on the page (will be changed to json)
        Page<Product> page=new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(productList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }
    //Query  product by id
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
        Product product = productService.getProductById(productId);
        if(product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    //create multiple products
    @PostMapping("/products")
    public ResponseEntity<List<Product>> createProducts(@RequestBody @Valid List<Product> productList){

        List<Product> createdProducts = new ArrayList<>();

        // Process each product request in the list
        for (Product product : productList) {
            Product productSaved = productService.createProduct(product);
            createdProducts.add(productSaved);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProducts);
    }
    //update product by id
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid Product receivedProduct){

        receivedProduct.setProductId(productId);
        Product updatedProduct = productService.updateProduct(receivedProduct);

        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/products/{productId}") //delete product by id
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){
        //check product if exists
        Product checkedProduct =productService.getProductById(productId);
        if (checkedProduct==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        productService.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
