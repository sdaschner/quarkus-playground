package com.sebastian_daschner.coffee.boundary;

import com.sebastian_daschner.coffee.entity.CoffeeBean;
import com.sebastian_daschner.coffee.entity.Roast;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

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

    @POST
    public void create(@Valid @NotNull JsonObject json) {
        String name = json.getString("name");
        Roast roast = Roast.valueOf(json.getString("roast"));

        Set<String> origins = json.getJsonArray("origins")
                .getValuesAs(JsonString.class).stream()
                .map(JsonString::getString)
                .collect(toSet());

        Map<String, Double> flavors = json.getJsonArray("flavorProfiles")
                .getValuesAs(JsonObject.class).stream()
                .collect(toMap(j -> j.getString("flavor"), j -> j.getJsonNumber("percentage").doubleValue()));

        coffeeBeans.createBean(name, roast, origins, flavors);
    }

    @GET
    @Path("special")
    public List<CoffeeBean> specialBeans() {
        return coffeeBeans.getCoffeeBeansWithUnexpectedFlavors();
    }


}
