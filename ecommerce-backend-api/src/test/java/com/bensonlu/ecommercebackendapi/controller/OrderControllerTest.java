package com.bensonlu.ecommercebackendapi.controller;

import com.bensonlu.ecommercebackendapi.dto.CreateOrderByItem;
import com.bensonlu.ecommercebackendapi.dto.CreateOrderRequest;
import com.bensonlu.ecommercebackendapi.repository.OrderRepository;
import com.bensonlu.ecommercebackendapi.service.OrderService;
import com.bensonlu.ecommercebackendapi.service.ProductService;
import com.bensonlu.ecommercebackendapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc; // To simulate HTTP requests
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private  SetUpTest setUpTest ;


     @BeforeEach
     public void setUp() {
        setUpTest.setUpUser();
        setUpTest.setUpProduct();
        setUpTest.setUpOrder();
    }

    // create an order
    @Transactional
    @Test
    public void createOrder_success() throws Exception {
        // create an order
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
    public void createOrder_illegalArgument_emptyBuyItemList() throws Exception {

        // create an empty order
        List<CreateOrderByItem> createOrderByItemList = new ArrayList<>();
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
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
    public void createOrder_userNotExist() throws Exception {
        // create an order
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        List<CreateOrderByItem> createOrderByItemList = new ArrayList<>();

        CreateOrderByItem createOrderByItem1 = new CreateOrderByItem();
        createOrderByItem1.setProductId(1);
        createOrderByItem1.setQuantity(1);
        createOrderByItemList.add(createOrderByItem1);

        createOrderRequest.setBuyItemList(createOrderByItemList);

        String json = objectMapper.writeValueAsString(createOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/orders", 100)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
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

    // get orders by user id
    @Test
    public void getOrders() throws Exception {

        // send the request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/orders", 1);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(1)))
                .andExpect(jsonPath("$.results[0].orderId", notNullValue()))
                .andExpect(jsonPath("$.results[0].userId", equalTo(1)))
                .andExpect(jsonPath("$.results[0].totalAmount", equalTo(10000)))
                .andExpect(jsonPath("$.results[0].orderItemList", hasSize(3)))
                .andExpect(jsonPath("$.results[0].createdDate", notNullValue()))
                .andExpect(jsonPath("$.results[0].lastModifiedDate", notNullValue()))
                ;
    }

    @Test
    public void getOrders_pagination() throws Exception {

        // send the request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/orders", 1)
                .param("limit", "2")
                .param("offset", "2");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(0)));
    }

    @Test
    public void getOrders_userHasNoOrder() throws Exception {

        // send the request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/orders", 2);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(0)));
    }

    @Test
    public void getOrders_userNotExist() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/orders", 100);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(0)));
    }
}