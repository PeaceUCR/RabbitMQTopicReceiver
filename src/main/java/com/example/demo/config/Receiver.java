package com.example.demo.config;

import org.springframework.stereotype.Component;


@Component
public class Receiver {


    public void receiveMessage1(String message) {
        System.out.println("Received <" + message + "> with routing key 1");
    }

    public void receiveMessage2(String message) {
        System.out.println("Received <" + message + "> with routing key 2");
    }
}
