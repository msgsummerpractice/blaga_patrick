package com.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        HelloWorld obj = context.getBean("helloWorldBean",HelloWorld.class);
        obj.printMessage();
        context.close();
    }
}
