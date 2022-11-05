package com.sebastian_daschner.coffee;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
@ApplicationScoped
public class CoffeeResource {

    @Inject
    CoffeeShop coffeeShop;

    @GET
    public String getCoffee() {
        return coffeeShop.getCoffee();
    }

}
