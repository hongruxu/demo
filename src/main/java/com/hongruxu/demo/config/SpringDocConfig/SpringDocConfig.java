package com.hongruxu.demo.config.SpringDocConfig;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("spring boot demo")
                        .version("1.0")
                        .description("这是一个简单spring boot demo")
                        .contact(new Contact()
                                .name("michael")
                                .email("michaelxu365@@gmail.com")));
    }
 
    // 分组定义
    @Bean
    public GroupedOpenApi helloApi(){
        return GroupedOpenApi.builder()
        .group("hello")
        .pathsToMatch("/hello/**")
        .build();
    }
    @Bean
    public GroupedOpenApi userApi(){
        return GroupedOpenApi.builder()
        .group("user")
        .pathsToMatch("/user/**")
        .build();
    }
    @Bean
    public GroupedOpenApi accountAPI(){
        return GroupedOpenApi.builder()
        .group("account")
        .pathsToMatch("/account/**", "/transfer/**")
        .build();
    }

}