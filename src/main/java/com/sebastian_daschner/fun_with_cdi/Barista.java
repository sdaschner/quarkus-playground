package com.sebastian_daschner.fun_with_cdi;

import io.quarkus.arc.DefaultBean;

import javax.enterprise.inject.Produces;

public class Barista {

    @Produces
    @DefaultBean
    public String makeCoffee() {
        return "coffee!";
    }

}
