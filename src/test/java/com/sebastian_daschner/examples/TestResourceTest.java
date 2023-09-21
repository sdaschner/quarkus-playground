package com.sebastian_daschner.examples;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

class StatusIT {

    @Test
    public void test() {
        given()
                .when().get("/test/123")
                .then()
                .statusCode(200)
                .body(is("123"));

        given()
                .when().get("/test/234")
                .then()
                .statusCode(404);
    }

}