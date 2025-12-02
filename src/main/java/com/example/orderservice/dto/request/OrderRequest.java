package com.example.orderservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/** DTO для создания заказа
 */
@Data
public class OrderRequest {
    @NotBlank(message = "Описание не может быть пустым")
    private String description;
}
