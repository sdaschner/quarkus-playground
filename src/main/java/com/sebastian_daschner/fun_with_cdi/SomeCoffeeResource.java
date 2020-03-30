package com.sebastian_daschner.fun_with_cdi;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@ApplicationScoped
@Path("some-coffee")
public class SomeCoffeeResource {

    @ConfigProperty(name = "coffee_mug")
    String coffeeMug;

    @Inject
    String coffee;

    @GET
    public String coffee() {
        return coffee + coffeeMug;
    }
}
