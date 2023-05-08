package com.sebastian_daschner.coffee;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import static jakarta.ws.rs.core.HttpHeaders.LOCATION;
import static jakarta.ws.rs.core.Response.Status.MOVED_PERMANENTLY;

@Path("/")
@ApplicationScoped
public class RootResource {

    @GET
    public Response redirect() {
        return Response
                .status(MOVED_PERMANENTLY)
                .header(LOCATION, "/orders.html")
                .build();
    }

}
