package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI serverMonitoringOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Server Monitoring API")
                        .description("API pour la gestion et le monitoring des serveurs")
                        .version("1.0.0"));
    }
}