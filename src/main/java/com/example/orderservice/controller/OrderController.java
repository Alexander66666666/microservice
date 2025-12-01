package com.example.orderservice.controller;

import com.example.orderservice.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.orderservice.dto.request.OrderRequest;
import com.example.orderservice.dto.response.OrderResponse;
import com.example.orderservice.entity.Order;
import com.example.orderservice.constant.OrderStatus;
import com.example.orderservice.entity.User;
import com.example.orderservice.security.UserDetailsImpl;
import com.example.orderservice.service.interfaces.OrderService;
import com.example.orderservice.service.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order Controller", description = "Управление заказами")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @Operation(summary = "Создать заказ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Заказ создан"),
            @ApiResponse(responseCode = "400", description = "Ошибка в данных")
    })
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Order order = orderService.createOrder(request, user);
        return ResponseEntity.ok(mapToOrderResponse(order));
    }
    @Operation(summary = "Получить мои заказы")
    @ApiResponse(responseCode = "200", description = "Список заказов")
    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getUserOrders(Pageable pageable, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Page<Order> orders = orderService.getUserOrders(user, pageable);
        return ResponseEntity.ok(orders.map(this::mapToOrderResponse));
    }

    @Operation(summary = "Получить все заказы (ADMIN)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список всех заказов"),
            @ApiResponse(responseCode = "403", description = "недостаточно прав")
    })
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<OrderResponse>> getAllOrders(Pageable pageable) {
        Page<Order> orders = orderService.getAllOrders(pageable);
        return ResponseEntity.ok(orders.map(this::mapToOrderResponse));
    }

    @Operation(summary = "Обновить статус заказа (ADMIN)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Статус обновлен"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateOrderStatus(@PathVariable UUID id, @RequestParam String status) {
        try {
            Order order = orderService.updateOrderStatus(id, OrderStatus.valueOf(status));
            return ResponseEntity.ok(mapToOrderResponse(order));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Удалить заказ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Заказ удален"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable UUID id, Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            User user = userService.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

            if (!isAdmin && !orderService.isOrderOwner(id, user)) {
                return ResponseEntity.badRequest().body("Error: You can only delete your own orders!");
            }

            orderService.deleteOrder(id);
            return ResponseEntity.ok("Order deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private OrderResponse mapToOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setDescription(order.getDescription());
        response.setStatus(order.getStatus());
        response.setCreatedAt(order.getCreatedAt());
        response.setUsername(order.getUser().getUsername());
        return response;
    }
}
