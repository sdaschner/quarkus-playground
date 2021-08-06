package com.sebastian_daschner.coffee;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class CoffeeShopConfig {

    @Inject
    ComplexConfiguration configuration;

    @ConfigProperty(name = "complex.list")
    List<String> list;
    
    public String getConfig() {
        System.out.println("configuration.coffee() = " + configuration.coffee());
        System.out.println("configuration.list() = " + configuration.list());
        System.out.println("configuration.someOther().coffee() = " + configuration.someOther().coffee());
        System.out.println("configuration.someOther().list() = " + configuration.someOther().list());

        System.out.println("list = " + list);

        return configuration.coffee();
    }
}
