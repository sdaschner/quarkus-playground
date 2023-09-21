package com.sebastian_daschner.examples;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@ApplicationScoped
@Path("test")
public class TestResource {

    @GET
    @Path("{name}")
    public String test(@PathParam("name") String name) {
        if (!"123".equals(name))
            throw new NotFoundException();
        return "123";
    }

}
