package com.example.orderservice.service.interfaces;

import com.example.orderservice.dto.request.OrderRequest;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderStatus;
import com.example.orderservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    Order createOrder(OrderRequest request, User user);
    List<Order> getUserOrders(User user);
    Page<Order> getUserOrders(User user, Pageable pageable);
    Page<Order> getAllOrders(Pageable pageable);
    Order updateOrderStatus(UUID orderId, OrderStatus status);
    void deleteOrder(UUID orderId);
    Optional<Order> findById(UUID orderId);
    boolean isOrderOwner(UUID orderId, User user);
}
