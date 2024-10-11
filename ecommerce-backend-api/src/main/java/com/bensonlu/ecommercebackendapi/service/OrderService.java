package com.bensonlu.ecommercebackendapi.service;

import com.bensonlu.ecommercebackendapi.dto.CreateOrderByItem;
import com.bensonlu.ecommercebackendapi.dto.OrderQueryParams;
import com.bensonlu.ecommercebackendapi.entity.Order;

import java.util.List;

public interface OrderService {


    Long countByUserId(OrderQueryParams orderQueryParams);
    List<Order> getOrders(OrderQueryParams orderQueryParameter);
    Order createOrder(Integer userId, List<CreateOrderByItem> itemList);
}
