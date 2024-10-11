package com.bensonlu.ecommercebackendapi.repository;

import com.bensonlu.ecommercebackendapi.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
