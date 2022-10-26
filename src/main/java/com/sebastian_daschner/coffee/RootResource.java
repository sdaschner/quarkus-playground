package com.sebastian_daschner.coffee;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.HttpHeaders.LOCATION;
import static javax.ws.rs.core.Response.Status.MOVED_PERMANENTLY;

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
