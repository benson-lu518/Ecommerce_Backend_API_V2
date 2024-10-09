package com.bensonlu.ecommercebackendapi.service.impl;

import com.bensonlu.ecommercebackendapi.dto.CreateOrderByItem;
import com.bensonlu.ecommercebackendapi.dto.OrderQueryParams;
import com.bensonlu.ecommercebackendapi.model.Order;
import com.bensonlu.ecommercebackendapi.model.OrderItem;
import com.bensonlu.ecommercebackendapi.model.Product;
import com.bensonlu.ecommercebackendapi.repository.OrderItemRepository;
import com.bensonlu.ecommercebackendapi.repository.OrderRepository;
import com.bensonlu.ecommercebackendapi.repository.ProductRepository;
import com.bensonlu.ecommercebackendapi.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final static Logger log= LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Override
    public Long countByUserId(OrderQueryParams orderQueryParams) {
        return  orderRepository.countByUserId(orderQueryParams.getUserId());
    }
    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        // Paging and sorting
        Pageable pageable = PageRequest.of(
                orderQueryParams.getOffset(),
                orderQueryParams.getLimit(),
                Sort.by("orderId").descending()
        );

        // Fetch the list of orders for the user
        List<Order> orderList = orderRepository.findByUserId(orderQueryParams.getUserId(), pageable);


        return orderList;
    }
//
//    @Override
//    public Order getOrderById(Integer orderId) {
//        return null;
//    }
//
    @Transactional  //will rollback if throw exception
    @Override
    public Order createOrder(Integer userId, List<CreateOrderByItem> itemList) {

        int totalAmount = 0;
        for (CreateOrderByItem item : itemList) {
            // check if product exists
            Product product = productRepository.findById(item.getProductId()).orElseThrow(() -> {
                log.warn("Product with ID {} not found when creating order for userID {}", item.getProductId(), userId);
                return new RuntimeException("Product not found");
            });
            // Check if product stock is sufficient
            if (product.getStock() < item.getQuantity()) {
                log.warn("Insufficient stock for productID {} for userID {}: required {}, available {}",
                        item.getProductId(), userId, item.getQuantity(), product.getStock());
                throw new RuntimeException("Insufficient stock for productId: " + item.getProductId());
            }
            // Deduct the stock
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
            // Calculate the total amount of the order
            totalAmount += product.getPrice() * item.getQuantity();
        }
        // Save the order
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setCreatedDate(new Date());
        order.setLastModifiedDate(new Date());
        order = orderRepository.save(order);

        // Save Order Items
        for (CreateOrderByItem item : itemList) {
            OrderItem orderItem = new OrderItem();
            Product product = productRepository.findByProductId(item.getProductId());
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setAmount(product.getPrice() * item.getQuantity());
            orderItemRepository.save(orderItem);
        }

        return order;
    }
}
