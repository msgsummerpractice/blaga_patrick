package com.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Car car = context.getBean(Car.class);
        car.drive();
        context.close();
    }
}
