import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CoffeeShopTest {

    @Test
    void testCoffee() {
        Coffee test = new Coffee();
        assertThat(test.getCoffee()).isEqualTo("Coffee");
    }

}