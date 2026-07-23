package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example")
public class AppConfig {

    @Bean
    public HelloWorld helloWorldBean() {
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.setMessage("Hello, Spring!");
        return helloWorld;
    }
    
}
