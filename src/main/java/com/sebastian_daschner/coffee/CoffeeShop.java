package com.sebastian_daschner.coffee;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CoffeeShop {

    public String getCoffee() {
        return "Coffee";
    }

}
