package com.example;
import org.springframework.stereotype.Component;


@Component("v8Engine")
public class V8Engine implements Engine {

    @Override
    public void start() {
        System.out.println("V8 engine started.");
    }
    
}
