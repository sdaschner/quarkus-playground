package com.sebastian_daschner.zero_downtime_k8s;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("coffees")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class CoffeesResource {

    @Inject
    CoffeeShop coffeeShop;

    @GET
    public List<Coffee> getCoffees() {
        return coffeeShop.getCoffees();
    }

    @POST
    public void addCoffee(JsonObject object) {
        String type = object.getString("type", null);

        if (type == null)
            throw new BadRequestException();

        coffeeShop.addCoffee(type);
    }

}
