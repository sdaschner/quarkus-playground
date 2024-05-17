import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloIT {

    private final System system = new System();

    @Test
    void testHello() {
        assertThat(system.sayHello()).isEqualTo("Hello");
    }

    @Test
    void testHelloParam() {
        assertThat(system.sayHello("World")).isEqualTo("Hello, World");
    }

    @AfterEach
    void tearDown() {
        system.close();
    }

}
