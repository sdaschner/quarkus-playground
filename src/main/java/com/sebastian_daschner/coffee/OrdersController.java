package com.sebastian_daschner.coffee;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
