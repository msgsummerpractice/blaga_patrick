package com.example;
import org.springframework.stereotype.Component;

@Component("v6Engine")
public class V6Engine implements Engine {

    @Override
    public void start() {
        System.out.println("V6 engine started.");
    }
    
}
