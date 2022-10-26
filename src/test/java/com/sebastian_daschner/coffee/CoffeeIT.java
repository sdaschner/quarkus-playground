package com.sebastian_daschner.coffee;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class CoffeeIT {

    private final CoffeeSystem coffeeSystem = new CoffeeSystem();

    @Test
    void testHello() {
        assertThat(coffeeSystem.getCoffee()).isEqualTo("Coffee.");
    }

    @Test
    void testCreateCoffeeOrder() {
        URI coffee = coffeeSystem.createCoffee("Latte");

        Coffee loadedCoffee = coffeeSystem.retrieveCoffee(coffee);
        assertThat(loadedCoffee.type).isEqualTo("Latte");
    }

    @AfterEach
    void tearDown() {
        coffeeSystem.close();
    }

}
