package com.sebastian_daschner;

import com.sebastian_daschner.coffee.CoffeeShop;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("root")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class RootResource {

    @Inject
    CoffeeShop coffeeShop;

    @Context
    ResourceContext resourceContext;

    @Path("sub")
    public SubResource subResource() {
        return resourceContext.getResource(SubResource.class);
    }

    @GET
    public String hello() {
        return "hello, from root, " + coffeeShop.getCoffee();
    }

}
