package com.sebastian_daschner.coffee;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CoffeeRepository implements PanacheRepositoryBase<Coffee, UUID> {

    public List<Coffee> listAllEspressos() {
        return list("type", "Espresso");
    }

}