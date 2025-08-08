package com.example.react_gametime.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI gameTimeOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Game Time Tracker API")
                        .description("API for managing game time requests and approvals")
                        .version("1.0"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("game-time")
                .pathsToMatch("/api/**")
                .build();
    }
}
