package com.bensonlu.ecommercebackendapi.controller;

import com.bensonlu.ecommercebackendapi.constant.ProductCategory;
import com.bensonlu.ecommercebackendapi.dto.CreateOrderByItem;
import com.bensonlu.ecommercebackendapi.entity.Product;
import com.bensonlu.ecommercebackendapi.entity.User;
import com.bensonlu.ecommercebackendapi.service.OrderService;
import com.bensonlu.ecommercebackendapi.service.ProductService;
import com.bensonlu.ecommercebackendapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class SetUpTest {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;


    @Transactional
    public void setUpProduct() {
        // create products
        Product product1 = new Product();
        product1.setProductName("Audi");
        product1.setCategory(ProductCategory.CAR);
        product1.setImageUrl("http://test");
        product1.setPrice(1000);
        product1.setStock(100);
        product1.setDescription("This is an car！");
        productService.createProduct(product1);
        Product product2 = new Product();
        product2.setProductName("Apple");
        product2.setCategory(ProductCategory.FOOD);
        product2.setImageUrl("http://test");
        product2.setPrice(500);
        product2.setStock(100);
        productService.createProduct(product2);
        Product product3 = new Product();
        product3.setProductName("Economics");
        product3.setCategory(ProductCategory.E_BOOK);
        product3.setImageUrl("http://test");
        product3.setPrice(200);
        product3.setStock(100);
        productService.createProduct(product3);

    }

    @Transactional
    public void setUpUser() {
        // create  users
        User user1 = new User();
        user1.setEmail("test1@gmail.com");
        user1.setPassword("123");
        userService.register(user1);
        User user2 = new User();
        user2.setEmail("test2@gamil.com");
        user2.setPassword("123");
        userService.register(user2);
    }
    @Transactional
    public void setUpOrder() {
        // create order for userid:1
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

        orderService.createOrder(1, createOrderByItemList);
    }
}
