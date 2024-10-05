package com.bensonlu.ecommercebackendapi.service.impl;

import com.bensonlu.ecommercebackendapi.dao.OrderDao;
import com.bensonlu.ecommercebackendapi.dao.ProductDao;
import com.bensonlu.ecommercebackendapi.dao.UserDao;
import com.bensonlu.ecommercebackendapi.dto.BuyItem;
import com.bensonlu.ecommercebackendapi.dto.CreateOrderRequest;
import com.bensonlu.ecommercebackendapi.dto.OrderQueryParams;
import com.bensonlu.ecommercebackendapi.model.Order;
import com.bensonlu.ecommercebackendapi.model.OrderItem;
import com.bensonlu.ecommercebackendapi.model.Product;
import com.bensonlu.ecommercebackendapi.model.User;
import com.bensonlu.ecommercebackendapi.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    private final static Logger log= LoggerFactory.getLogger(OrderServiceImpl.class);

    //insert mutilple Dao in service layer
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        return orderDao.countOrder(orderQueryParams);
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        List<Order> orderList=orderDao.getOrders(orderQueryParams);

        for(Order order : orderList){
            List<OrderItem> orderItemList=orderDao.getOrderItemsByOrderId(order.getOrderId());
            //get orderitems info

            order.setOrderItemList(orderItemList);
        }

        return orderList;

    }

    @Override
    public Order getOrderById(Integer orderId) {
        Order order =orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList=orderDao.getOrderItemsByOrderId(orderId);

        order.setOrderItemList(orderItemList); //add order information into order

        return order;
    }

    @Transactional  //will rollback if throw exception
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        //check if user exists
        User user =userDao.getUserById(userId);

        if(user==null){
            log.warn("userId: {} not exist", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //caculate total amount

        int totalAmount=0;
        List<OrderItem> orderItemList=new ArrayList<>();
        for(BuyItem buyItem: createOrderRequest.getBuyItemList()){
            //get product information
            Product product=productDao.getProductById(buyItem.getProductId());

            //check product if exist
            if(product==null){
                log.warn("product {} not exist",buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            //check product if it is sufficient
            }else if (product.getStock()< buyItem.getQuantity()){
                log.warn("product {} is insufficient stock. Remaining stock: {}, the quantity you request: {}",
                        buyItem.getProductId(),product.getStock(),buyItem.getQuantity());
                throw new ResponseStatusException((HttpStatus.BAD_REQUEST));
            }

            //if it exists then deduct the stock quantity
            productDao.updateStock(product.getProductId(),product.getStock()- buyItem.getQuantity());


            //caculate amount
            int amount=buyItem.getQuantity()*product.getPrice();
            totalAmount=totalAmount+amount;

            //transfer buyItem to OrderItem
            OrderItem orderItem=new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);
            orderItemList.add(orderItem);
        }


        //create order
        Integer orderId=orderDao.createOrder(userId,totalAmount);

        //create orderItems
        orderDao.createOrderItems(orderId,orderItemList);

        return orderId;
    }
}
