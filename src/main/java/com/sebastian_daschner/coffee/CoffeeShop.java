package com.sebastian_daschner.coffee;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class CoffeeShop {

    @Inject
    CoffeeRepository repository;

    public String getCoffee() {
        return "Coffee";
    }

    public Coffee getCoffee(UUID id) {
        return repository.findById(id);
    }

    public List<Coffee> getCoffees() {
//        return repository.listAllEspressos();
        return repository.listAll();
    }

    public UUID addCoffee(String type) {
        Coffee coffee = new Coffee(type);
        repository.persist(coffee);
        return coffee.id;
    }

}
