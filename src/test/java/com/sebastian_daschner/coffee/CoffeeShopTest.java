package com.sebastian_daschner.coffee;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CoffeeShopTest {

    private final CoffeeShop coffeeShop = new CoffeeShop();

    @Test
    void testCoffee() {
        assertThat(coffeeShop.getCoffee()).isEqualTo("Coffee.");
    }

}