package com.sebastian_daschner.coffee;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CoffeeSmokeIT {

    private final CoffeeSystem coffeeSystem = new CoffeeSystem();

    @Test
    void testHello() {
        assertThat(coffeeSystem.getCoffee()).isEqualTo("Coffee");
    }

    @AfterEach
    void tearDown() {
        coffeeSystem.close();
    }

}
