package com.sebastian_daschner.zero_downtime_k8s;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class AnotherCoffeeResourceIT {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/coffee")
                .then()
                .body(is("Coffee"));
    }

}
