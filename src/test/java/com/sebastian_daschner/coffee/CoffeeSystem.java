package com.sebastian_daschner.coffee;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import java.net.URI;

public class CoffeeSystem {

    private final Client client;
    private final WebTarget baseTarget;

    public CoffeeSystem() {
        client = ClientBuilder.newBuilder().build();
        baseTarget = client.target(buildUri());
    }

    private URI buildUri() {
        String host = System.getProperty("coffee.test.host", "localhost");
        String port = System.getProperty("coffee.test.port", "8080");
        return UriBuilder.fromUri("http://{host}:{port}/").build(host, port);
    }

    public String getCoffee() {
        Response response = baseTarget.path("coffee").request().get();
        verifySuccess(response);
        return response.readEntity(String.class);
    }


    private void verifySuccess(Response response) {
        if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL)
            throw new AssertionError("Status was not successful: " + response.getStatus());
    }

    public void close() {
        client.close();
    }

}
