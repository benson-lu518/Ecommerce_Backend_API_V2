package com.bensonlu.ecommercebackendapi.controller;


import com.bensonlu.ecommercebackendapi.constant.ProductCategory;
import com.bensonlu.ecommercebackendapi.entity.Product;
import com.bensonlu.ecommercebackendapi.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerUpdateTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private  SetUpTest setUpTest ;


    @Transactional
    @Test
    public void updateProduct_success() throws Exception {

//        setUp();

        Product product2 = new Product();
        product2.setProductName("Audi(Modified)");
        product2.setPrice(2500);

        String json = objectMapper.writeValueAsString(product2); //object to json

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/products/{productId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.productName", equalTo("Audi(Modified)")))
                .andExpect(jsonPath("$.category", equalTo("CAR")))
                .andExpect(jsonPath("$.imageUrl", equalTo("https://test")))
                .andExpect(jsonPath("$.price", equalTo(2500)))
                .andExpect(jsonPath("$.stock", equalTo(100)))
                .andExpect(jsonPath("$.description", equalTo("This is a car！")))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
    }
    @Transactional
    @Test
    public void updateProduct_productNotFound() throws Exception {

        Product product1 = new Product();
        product1.setProductName("BMW(Modified)");
        product1.setCategory(ProductCategory.CAR);
        product1.setImageUrl("http://test");
        product1.setPrice(2000);
        product1.setDescription("This is an car！");

        String json = objectMapper.writeValueAsString(product1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/products/{productId}", 20000)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }
}