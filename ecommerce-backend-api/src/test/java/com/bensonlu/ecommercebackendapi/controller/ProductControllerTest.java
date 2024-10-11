package com.bensonlu.ecommercebackendapi.controller;

import com.bensonlu.ecommercebackendapi.constant.ProductCategory;
import com.bensonlu.ecommercebackendapi.entity.Product;
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
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private ProductService productService;
    @Autowired
    private  SetUpTest setUpTest ;


    @BeforeEach
    public void setUp() {
        setUpTest.setUpUser();
        setUpTest.setUpProduct();
        setUpTest.setUpOrder();
    }
    // query products
    @Test
    public void getProduct_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products/{productId}", 1);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName", equalTo("Audi")))
                .andExpect(jsonPath("$.category", equalTo("CAR")))
                .andExpect(jsonPath("$.imageUrl", equalTo("http://test")))
                .andExpect(jsonPath("$.price", equalTo(1000)))
                .andExpect(jsonPath("$.stock", equalTo(95)))
                .andExpect(jsonPath("$.description", notNullValue()))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
    }

    @Test
    public void getProduct_notFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products/{productId}", 20000);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));
    }

    @Transactional
    @Test
    public void createProduct_success() throws Exception {
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductName("Audi");
        product1.setCategory(ProductCategory.CAR);
        product1.setImageUrl("http://test");
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
                .andExpect(jsonPath("$[0].productName", equalTo("Audi")))
                .andExpect(jsonPath("$[0].category", equalTo("CAR")))
                .andExpect(jsonPath("$[0].imageUrl", equalTo("http://test")))
                .andExpect(jsonPath("$[0].price", equalTo(1000)))
                .andExpect(jsonPath("$[0].stock", equalTo(100)))
                .andExpect(jsonPath("$[0].description", equalTo("This is an car！")))
                .andExpect(jsonPath("$[0].createdDate", notNullValue()))
                .andExpect(jsonPath("$[0].lastModifiedDate", notNullValue()));
    }


    @Transactional
    @Test
    public void updateProduct_success() throws Exception {
        setUpTest.setUpProduct();

        Product product1 = new Product();
        product1.setProductName("Audi(Modified)");
        product1.setCategory(ProductCategory.CAR);
        product1.setImageUrl("http://test");
        product1.setPrice(2000);
        product1.setDescription("This is an car！");


        String json = objectMapper.writeValueAsString(product1); //object to json

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/products/{productId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.productName", equalTo("Audi(Modified)")))
                .andExpect(jsonPath("$.category", equalTo("CAR")))
                .andExpect(jsonPath("$.imageUrl", equalTo("http://test")))
                .andExpect(jsonPath("$.price", equalTo(2000)))
                .andExpect(jsonPath("$.stock", equalTo(95)))
                .andExpect(jsonPath("$.description", equalTo("This is an car！")))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
    }

    @Transactional
    @Test
    public void updateProduct_productNotFound() throws Exception {

        Product product1 = new Product();
        product1.setProductName("Audi(Modified)");
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

    @Transactional
    @Test
    public void deleteProduct_success() throws Exception {
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
//
//    // query product list
    @Test
    public void getProducts() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(3)));
    }

    @Test
    public void getProducts_filtering() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param("search", "A")
                .param("category", "CAR");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(1)));
    }

    @Test
    public void getProducts_sorting() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param("orderBy", "price")
                .param("sort", "asc");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(3)))
                .andExpect(jsonPath("$.results[0].productId", equalTo(3)))
                .andExpect(jsonPath("$.results[1].productId", equalTo(2)))
                .andExpect(jsonPath("$.results[2].productId", equalTo(1)))
                ;
    }

    @Test
    public void getProducts_pagination() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param("limit", "2")
                .param("offset", "2");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(1)))
                .andExpect(jsonPath("$.results[0].productId", equalTo(1)))
                ;
    }
}