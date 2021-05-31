package com.sebastian_daschner.coffee;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;

@QuarkusTest
public class CoffeeQuarkusTest {

    @Test
    void test() {
        RestAssured.given()
                .when()
                .get("/coffee")
                .then()
                .body(is("Coffee."));
    }

}
