package com.sebastian_daschner.coffee;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("orders.html")
@ApplicationScoped
@Produces(MediaType.TEXT_HTML)
public class OrdersController {

    @Location("orders.html")
    Template template;

    @Inject
    CoffeeShop coffeeShop;

    @GET
    public TemplateInstance orders() {
        List<Coffee> orders = coffeeShop.getCoffees();
        return template.data("orders", orders);
    }

}
