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

@Path("index.html")
@ApplicationScoped
@Produces(MediaType.TEXT_HTML)
public class CoffeeController {

    @Inject
    CoffeeShop coffeeShop;

    @Location("orders.html")
    Template template;

    @GET
    public TemplateInstance index() {
        List<Coffee> coffees = coffeeShop.getCoffees();
        return template.data("coffees", coffees);
    }

}
