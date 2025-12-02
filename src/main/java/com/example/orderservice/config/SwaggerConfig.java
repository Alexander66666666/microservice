package com.example.orderservice.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация Swagger/OpenAPI для генерации документации API.
 */
@Configuration
public class SwaggerConfig {

    /** Настройка OpenAPI документации. */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Order Service API")
                        .version("1.0")
                        .description("API для управления заказами"))
                .addServersItem(new Server().url("/").description("Default Server"));
    }
}
