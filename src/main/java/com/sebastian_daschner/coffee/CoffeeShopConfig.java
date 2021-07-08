package com.sebastian_daschner.coffee;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CoffeeShopConfig {

    @Inject
    ComplexConfiguration configuration;

    public String getConfig() {
        System.out.println("configuration.coffee() = " + configuration.coffee());
        System.out.println("configuration.list() = " + configuration.list());
        System.out.println("configuration.someOther().coffee() = " + configuration.someOther().coffee());
        System.out.println("configuration.someOther().list() = " + configuration.someOther().list());

        return configuration.coffee();
    }
}
