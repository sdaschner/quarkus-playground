package com.sebastian_daschner.cars;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class CarRepository implements PanacheRepository<Car> {

    public Set<ColorTireCombination> findColorTireCombinations() {
        return getEntityManager().createQuery("""
                        SELECT new com.sebastian_daschner.cars.ColorTireCombination(
                            c.color, c.tireType)
                        FROM Car c
                        """, ColorTireCombination.class).getResultStream()
                .collect(Collectors.toSet());
    }

}
