package com.sebastian_daschner.cars;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Set;

@Path("cars")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class CarsResource {


    @Inject
    Cars cars;

    @GET
    public List<Car> cars() {
        return cars.listAllCars();
    }

    @POST
    public Response create() {
        Car car = cars.createCar();
        return Response.created(URI.create("/cars/" + car.id)).build();
    }

    @GET
    @Path("colors-tires")
    public Set<ColorTireCombination> findAllColorTireCombinations() {
        return cars.findAllColorTireCombinations();
    }

}
