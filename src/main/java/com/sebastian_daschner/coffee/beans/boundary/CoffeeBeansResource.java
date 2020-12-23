package com.sebastian_daschner.coffee.beans.boundary;

import com.sebastian_daschner.coffee.beans.entity.CoffeeBean;
import com.sebastian_daschner.coffee.beans.entity.Roast;

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
import java.util.UUID;

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
    @Path("{name}")
    public CoffeeBean bean(@PathParam("name") String name) {
        CoffeeBean bean = coffeeBeans.getCoffeeBean(name);
        if (bean == null)
            throw new NotFoundException();
        return bean;
    }

    @PATCH
    @Path("{name}")
    public Response updateBean(@PathParam("name") String name, JsonObject json) {

        Map<String, Double> flavors = json.getJsonArray("flavorProfiles")
                .getValuesAs(JsonObject.class).stream()
                .collect(toMap(j -> j.getString("flavor"), j -> j.getJsonNumber("percentage").doubleValue()));

        UUID actionId = coffeeBeans.updateBeanFlavors(name, flavors);

        return Response.noContent().header("Action-Id", actionId).build();
    }

    @DELETE
    @Path("{name}")
    public Response deleteBean(@PathParam("name") String name) {
        UUID actionId = coffeeBeans.deleteBean(name);
        return Response.noContent().header("Action-Id", actionId).build();
    }

    @GET
    @Path("special")
    public List<CoffeeBean> specialBeans() {
        return coffeeBeans.getCoffeeBeansWithUnexpectedFlavors();
    }


}
