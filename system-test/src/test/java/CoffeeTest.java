import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class CoffeeTest {

    private final CoffeeSystem coffeeSystem = new CoffeeSystem();

    @Test
    void testHello() {
        assertThat(coffeeSystem.getCoffee()).isEqualTo("Coffee");
    }

    @Test
    void testCreateOrder() {
        URI coffee = coffeeSystem.createCoffee("Espresso");

        Coffee loaded = coffeeSystem.retrieveCoffee(coffee);
        assertThat(loaded.type).isEqualTo("Espresso");
    }

    @AfterEach
    void tearDown() {
        coffeeSystem.close();
    }

}
