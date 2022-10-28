package com.sebastian_daschner.coffee;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.stream.JsonCollectors;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
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
