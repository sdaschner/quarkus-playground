package com.sebastian_daschner.coffee;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class CoffeeShop {

    private final Map<UUID, Coffee> coffees = new ConcurrentHashMap<>();

    public String getCoffee() {
        return "Coffee";
    }

    public Coffee getCoffee(UUID id) {
        return coffees.get(id);
    }

    public List<Coffee> getCoffees() {
        return new ArrayList<>(coffees.values());
    }

    public UUID addCoffee(String type) {
        Coffee coffee = new Coffee(type);
        coffee.id = UUID.randomUUID();
        coffees.put(coffee.id, coffee);
        return coffee.id;
    }

}
