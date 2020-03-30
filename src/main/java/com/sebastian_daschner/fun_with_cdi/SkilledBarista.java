package com.sebastian_daschner.fun_with_cdi;

import javax.enterprise.inject.Produces;

public class SkilledBarista {

    @Produces
    public String makeCoffee() {
        return "very tasty coffee!";
    }

}
