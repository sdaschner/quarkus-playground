package com.sebastian_daschner.zero_downtime_k8s;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.concurrent.CompletionStage;

@Path("coffee")
@ApplicationScoped
public class CoffeeResource {

    @Inject
    CoffeeShop coffeeShop;

    @GET
    public String getCoffeeShop() {
        return coffeeShop.getCoffee();
    }

    @Path("count")
    @GET
    public long counter() {
        return coffeeShop.getCount();
    }

    @Path("async")
    @GET
    public CompletionStage<String> getAsyncCoffee() {
        return coffeeShop.getAsyncCoffee();
    }


}
