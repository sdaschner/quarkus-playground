package com.sebastian_daschner.coffee;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("orders.html")
@ApplicationScoped
@Produces(MediaType.TEXT_HTML)
public class OrdersController {

    @Inject
    CoffeeShop coffeeShop;

    @Location("orders.html")
    Template template;

    @GET
    public TemplateInstance orders() {
        List<Coffee> orders = coffeeShop.getCoffees();
        return template.data("coffees", orders);
    }

}
