package com.sebastian_daschner.coffee;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.stream.JsonCollectors;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.UUID;

@Path("coffees")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class CoffeesResource {

    @Inject
    CoffeeShop coffeeShop;

    @Context
    UriInfo uriInfo;

    @GET
    public JsonArray getCoffees() {
        return coffeeShop.getCoffees().stream()
                .map(c -> Json.createObjectBuilder()
                        .add("type", c.type)
                        .add("_self", uriInfo.getBaseUriBuilder()
                                .path(CoffeesResource.class)
                                .path(CoffeesResource.class, "getCoffee")
                                .build(c.id).toString())
                        .build())
                .collect(JsonCollectors.toJsonArray());
    }

    @GET
    @Path("{id}")
    public JsonObject getCoffee(@PathParam("id") UUID id) {
        return Json.createObjectBuilder()
                .add("type", coffeeShop.getCoffee(id).type)
                .build();
    }

    @POST
    public Response createCoffee(JsonObject object) {
        String type = object.getString("type", null);

        if (type == null)
            throw new BadRequestException();

        UUID uuid = coffeeShop.addCoffee(type);

        URI uri = uriInfo.getBaseUriBuilder()
                .path(CoffeesResource.class)
                .path(CoffeesResource.class, "getCoffee")
                .build(uuid);

        return Response.created(uri).build();
    }

}
