package com.sebastian_daschner.quarkus;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Hello {

    public String sayHello() {
        return "Hello";
    }

    public String sayHello(String name) {
        return "Hello, " + name;
    }
}
