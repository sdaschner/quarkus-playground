package com.sebastian_daschner.cars;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class Cars {

    @Inject
    CarRepository repository;

    public List<Car> listAllCars() {
        return repository.listAll();
    }

    @Transactional
    public Car createCar() {
        Car car = new Car();
        car.color = "blue";
        car.tireType = new Tire(TireSpeedRating.Z, 205);
        repository.persist(car);
        return car;
    }

    public Set<ColorTireCombination> findAllColorTireCombinations() {
        return repository.findColorTireCombinations();
    }

}
