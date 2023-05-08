package com.sebastian_daschner.coffee;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("coffee")
@ApplicationScoped
public class CoffeeResource {

    @Inject
    CoffeeShop coffeeShop;

    @GET
    public String getCoffee() {
        return coffeeShop.getCoffee();
    }

}
