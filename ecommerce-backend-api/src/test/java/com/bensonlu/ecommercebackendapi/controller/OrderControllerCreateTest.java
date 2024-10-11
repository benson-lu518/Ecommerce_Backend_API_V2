package com.bensonlu.ecommercebackendapi.controller;

import com.bensonlu.ecommercebackendapi.dto.CreateOrderByItem;
import com.bensonlu.ecommercebackendapi.dto.CreateOrderRequest;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerCreateTest {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc; // To simulate HTTP requests
    @Autowired
    private  SetUpTest setUpTest ;


    @Transactional
    @Test
    public void createOrder_success() throws Exception {


        // create an order with three items
        List<CreateOrderByItem> createOrderByItemList = new ArrayList<>();

        CreateOrderByItem createOrderByItem1 = new CreateOrderByItem();
        createOrderByItem1.setProductId(1);
        createOrderByItem1.setQuantity(5); // 5 * 1000 = 5000
        createOrderByItemList.add(createOrderByItem1);

        CreateOrderByItem createOrderByItem2 = new CreateOrderByItem();
        createOrderByItem2.setProductId(2);
        createOrderByItem2.setQuantity(2); // 2 * 500 = 1000
        createOrderByItemList.add(createOrderByItem2);

        CreateOrderByItem createOrderByItem3 = new CreateOrderByItem();
        createOrderByItem3.setProductId(3);
        createOrderByItem3.setQuantity(20); // 20 * 200 = 4000
        createOrderByItemList.add(createOrderByItem3);
        // object to json
        String json = objectMapper.writeValueAsString(createOrderByItemList);
        // build the request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/orders", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        // sent the request
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.orderId", notNullValue()))
                .andExpect(jsonPath("$.userId", equalTo(1)))
                .andExpect(jsonPath("$.totalAmount", equalTo(10000)))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
    }

    @Transactional
    @Test
    public void createOrder_userNotExist() throws Exception {

        // create an order with three items
        List<CreateOrderByItem> createOrderByItemList = new ArrayList<>();

        CreateOrderByItem createOrderByItem1 = new CreateOrderByItem();
        createOrderByItem1.setProductId(1);
        createOrderByItem1.setQuantity(5); // 5 * 1000 = 5000
        createOrderByItemList.add(createOrderByItem1);

        CreateOrderByItem createOrderByItem2 = new CreateOrderByItem();
        createOrderByItem2.setProductId(2);
        createOrderByItem2.setQuantity(2); // 2 * 500 = 1000
        createOrderByItemList.add(createOrderByItem2);

        CreateOrderByItem createOrderByItem3 = new CreateOrderByItem();
        createOrderByItem3.setProductId(3);
        createOrderByItem3.setQuantity(20); // 20 * 200 = 4000
        createOrderByItemList.add(createOrderByItem3);
        // object to json
        String json = objectMapper.writeValueAsString(createOrderByItemList);
        // build the request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/orders", 100)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        // sent the request
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400))
               ;
    }


    @Transactional
    @Test
    public void createOrder_productNotExist() throws Exception {

        // create an order
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        List<CreateOrderByItem> createOrderByItemList = new ArrayList<>();

        CreateOrderByItem createOrderByItem1 = new CreateOrderByItem();
        createOrderByItem1.setProductId(100);
        createOrderByItem1.setQuantity(1);
        createOrderByItemList.add(createOrderByItem1);

        createOrderRequest.setBuyItemList(createOrderByItemList);

        String json = objectMapper.writeValueAsString(createOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/orders", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Transactional
    @Test
    public void createOrder_stockNotEnough() throws Exception {

        // create an order
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        List<CreateOrderByItem> createOrderByItemList = new ArrayList<>();

        CreateOrderByItem createOrderByItem1 = new CreateOrderByItem();
        createOrderByItem1.setProductId(1);
        createOrderByItem1.setQuantity(100000);
        createOrderByItemList.add(createOrderByItem1);

        createOrderRequest.setBuyItemList(createOrderByItemList);

        String json = objectMapper.writeValueAsString(createOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/orders", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

}
