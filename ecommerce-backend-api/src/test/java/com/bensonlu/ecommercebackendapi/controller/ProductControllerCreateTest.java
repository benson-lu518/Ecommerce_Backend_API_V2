package com.bensonlu.ecommercebackendapi.controller;

import com.bensonlu.ecommercebackendapi.constant.ProductCategory;
import com.bensonlu.ecommercebackendapi.entity.Product;
import com.bensonlu.ecommercebackendapi.repository.ProductRepository;
import com.bensonlu.ecommercebackendapi.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerCreateTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private  SetUpTest setUpTest ;



    @Transactional
    @Test
    public void createProduct_success() throws Exception {
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductName("BMW");
        product1.setCategory(ProductCategory.CAR);
        product1.setImageUrl("https://test");
        product1.setPrice(1000);
        product1.setStock(100);
        product1.setDescription("This is an car！");

        productList.add(product1);

        String json = objectMapper.writeValueAsString(productList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$[0].productName", equalTo("BMW")))
                .andExpect(jsonPath("$[0].category", equalTo("CAR")))
                .andExpect(jsonPath("$[0].imageUrl", equalTo("https://test")))
                .andExpect(jsonPath("$[0].price", equalTo(1000)))
                .andExpect(jsonPath("$[0].stock", equalTo(100)))
                .andExpect(jsonPath("$[0].description", equalTo("This is an car！")))
                .andExpect(jsonPath("$[0].createdDate", notNullValue()))
                .andExpect(jsonPath("$[0].lastModifiedDate", notNullValue()));
    }



    @Transactional
    @Test
    public void deleteProduct_success() throws Exception {
//        setUpTest.setUpProduct();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/products/{productId}", 3);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204)); //204: Request successful, no content returned.
    }

    @Transactional
    @Test
    public void deleteProduct_deleteNonExistingProduct() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/products/{productId}", 20000);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));
    }

}