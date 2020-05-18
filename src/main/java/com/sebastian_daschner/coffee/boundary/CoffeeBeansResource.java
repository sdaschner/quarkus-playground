package com.sebastian_daschner.coffee.boundary;

import com.sebastian_daschner.coffee.entity.CoffeeBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("beans")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class CoffeeBeansResource {

    @Inject
    CoffeeBeans coffeeBeans;

    @GET
    public List<CoffeeBean> beans(@QueryParam("flavor") @DefaultValue("") String flavor) {
        if (!flavor.isBlank())
            return coffeeBeans.getCoffeeBeansSpecificFlavor(flavor.toUpperCase());
        return coffeeBeans.getCoffeeBeans();
    }

    @GET
    @Path("special")
    public List<CoffeeBean> specialBeans() {
        return coffeeBeans.getCoffeeBeansWithUnexpectedFlavors();
    }


}
