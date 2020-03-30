package com.sebastian_daschner.coffee;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class CoffeeShop {

    private Map<UUID, Coffee> store = new ConcurrentHashMap<>();

    public String getCoffee() {
        return "Coffee";
    }

    public Coffee getCoffee(UUID id) {
        return store.get(id);
    }

    public List<Coffee> getCoffees() {
        return new ArrayList<>(store.values());
    }

    public void addCoffee(String type) {
        Coffee coffee = new Coffee(type);
        store.put(coffee.id, coffee);
    }

}
