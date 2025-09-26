package com.hongruxu.demo.config.SpringDocConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot API")
                        .version("1.0")
                        .description("Spring Boot API Documentation")
                        .contact(new Contact()
                                .name("Developer")
                                .email("dev@example.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("Spring Boot Wiki Documentation")
                        .url("https://example.com"));
    }
 
}