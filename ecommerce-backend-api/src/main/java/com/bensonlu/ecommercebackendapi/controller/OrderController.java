package com.bensonlu.ecommercebackendapi.controller;

import com.bensonlu.ecommercebackendapi.dto.CreateOrderRequest;
import com.bensonlu.ecommercebackendapi.dto.OrderQueryParams;
import com.bensonlu.ecommercebackendapi.dto.ProductQueryParams;
import com.bensonlu.ecommercebackendapi.model.Order;
import com.bensonlu.ecommercebackendapi.model.Product;
import com.bensonlu.ecommercebackendapi.service.OrderService;
import com.bensonlu.ecommercebackendapi.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    //get order list
    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "10") @Max(100) @Min(0) Integer limit, //show limit page number
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
    ){

        OrderQueryParams orderQueryParams=new OrderQueryParams();
        orderQueryParams.setUserId(userId);
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);

        //get order list
        List<Order> orderList=orderService.getOrders(orderQueryParams);
        //get total order num
        Integer count=orderService.countOrder(orderQueryParams);

        //paging
        Page<Order> page=new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(count);
        page.setResults(orderList);
        return ResponseEntity.status(HttpStatus.OK).body(page);

    }


    //create order
    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest){

       Integer orderId =orderService.createOrder(userId,createOrderRequest);
       Order order=orderService.getOrderById(orderId); //according to orderId to get order info
       return ResponseEntity.status(HttpStatus.CREATED).body(order); //return order
    }


}
