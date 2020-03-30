package com.sebastian_daschner.coffee;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Path("coffees")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class CoffeesResource {

    @Inject
    CoffeeShop coffeeShop;

    @Inject
    Jsonb jsonb;

    @GET
    @Path("test-jsonb")
    public String testJsonB() {
        return jsonb.toJson(new Coffee("Espresso"));
    }

    @GET
    @Path("test-jsonp")
    public String testJsonP() {
        Coffee coffee = new Coffee("Espresso");
        return Json.createObjectBuilder()
                .add("type", coffee.type)
                .add("id", coffee.id.toString())
                .build()
                .toString();
    }

    @GET
    public List<Coffee> getCoffees() {
        return coffeeShop.getCoffees();
    }

    @GET
    @Path("{id}")
    public JsonObject getCoffee(@PathParam("id") UUID id) {
        return Json.createObjectBuilder()
                .add("type", coffeeShop.getCoffee(id).type)
                .build();
    }

    @POST
    public void addCoffee(JsonObject object) {
        String type = object.getString("type", null);

        if (type == null)
            throw new BadRequestException();

        coffeeShop.addCoffee(type);
    }

}
