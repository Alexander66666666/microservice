package com.example.orderservice.service.impl;

import com.example.orderservice.dto.request.OrderRequest;
import com.example.orderservice.entity.Order;
import com.example.orderservice.constant.OrderStatus;
import com.example.orderservice.entity.User;
import com.example.orderservice.exception.OrderNotFoundException;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.interfaces.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Реализация сервиса для управления заказами
 * Обеспечивает создание, получение, обновление и удаление заказов.
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    /**
     * Создает новый заказ для указанного пользователя.
     * Устанавливает статус заказа в CREATED и связывает его с пользователем.
     *
     * @param request DTO с данными для создания заказа
     * @param user пользователь, для котого создается заказ
     * @return созданный заказ
     */
    @Override
    @Transactional
    public Order createOrder(OrderRequest request, User user) {
        Order order = new Order();
        order.setDescription(request.getDescription());
        order.setUser(user);
        order.setStatus(OrderStatus.CREATED);

        return orderRepository.save(order);
    }

    /**
     * Получает список всех заказов пользователя.
     *
     * @param user пользователь, чьи заказы необходимо получить
     * @return список заказов пользователя
     */
    @Override
    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUser(user);
    }

    /**
     * Получает страницу заказов пользователя с поддержкой пагинации.
     *
     * @param user пользователь, чьи заказы необходимо получить
     * @param pageable параметры пагинации и сортировки
     * @return страница заказов пользователя
     */

    @Override
    public Page<Order> getUserOrders(User user, Pageable pageable) {
        return orderRepository.findByUser(user, pageable);
    }

    /**
     * Получает страницу ВСЕХ ЗАКАЗОВ В СИСТЕМЕ с поддержкой пагинации.
     *
     * @param pageable параметры пагинации и сортировки
     * @return станица всех заказов пользователя
     */
    @Override
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    /**
     * Обновляет статус заказа.
     *
     * @param orderId идентификатор заказа
     * @param status новый статус заказа
     * @return обновленный заказ
     * @throws OrderNotFoundException если заказ с указанным ID не найден
     */
    @Override
    @Transactional
    public Order updateOrderStatus(UUID orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found" + orderId));
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(UUID orderId) {
        orderRepository.deleteById(orderId);

    }

    /**
     * Находит заказ по идентификатору
     *
     * @param orderId идентификатор заказа
     * @return Optional с заказом, если найден, иначе пустой Optional
     */
    @Override
    public Optional<Order> findById(UUID orderId) {
        return orderRepository.findById(orderId);
    }

    /**
     * Проверяет, является ли пользователь владельцем заказа.
     *
     * @param orderId идентификатор заказа
     * @param user пользователь для проверки
     * @return true если пользователь является владельцем заказа, иначе false
     */
    @Override
    public boolean isOrderOwner(UUID orderId, User user) {
        return orderRepository.existsByIdAndUser(orderId, user);
    }
}

