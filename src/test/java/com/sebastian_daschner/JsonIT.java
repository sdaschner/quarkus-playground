package com.sebastian_daschner;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonIT {

    private Client client;
    private WebTarget baseTarget;

    @BeforeEach
    void setUp() {
        client = ClientBuilder.newBuilder().build();
        baseTarget = client.target("http://localhost:8080/json-test");
    }

    @Test
    void testJson() {
        Response response = baseTarget.request(MediaType.APPLICATION_JSON)
                .get();
        assertThat(response.getStatus()).isEqualTo(200);
        String json = response.readEntity(String.class);
        System.out.println("json = " + json);
    }

    @AfterEach
    void tearDown() {
        client.close();
    }

}
