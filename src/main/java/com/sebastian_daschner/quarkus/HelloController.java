package com.sebastian_daschner.quarkus;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("hello")
public class HelloController {

    @Inject
    Hello hello;

    @GET
    public String hello() {
        return hello.sayHello();
    }

    @GET
    @Path("{name}")
    public String hello(@PathParam("name") String name) {
        return hello.sayHello(name);
    }

}
