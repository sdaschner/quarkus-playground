package com.sebastian_daschner.zero_downtime_k8s;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class CoffeeResourceBeanTest {

    @Inject
    CoffeeResource coffeeResource;

    @Test
    void testCoffee() {
        assertThat(coffeeResource.getCoffeeShop()).isEqualTo("Coffee");
    }

}
