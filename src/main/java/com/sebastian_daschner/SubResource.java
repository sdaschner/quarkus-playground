package com.sebastian_daschner;

import com.sebastian_daschner.coffee.CoffeeShop;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class SubResource {

    @Inject
    CoffeeShop coffeeShop;

    @GET
    public String hello() {
        return "hello, from sub, " + coffeeShop.getCoffee();
    }

}
