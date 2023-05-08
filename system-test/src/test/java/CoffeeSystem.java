import jakarta.json.Json;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
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

    public URI createCoffee(String type) {
        Response response = baseTarget.path("coffees")
                .request()
                .post(Entity.json(Json.createObjectBuilder()
                        .add("type", type).build()));

        return URI.create(response.getHeaderString(HttpHeaders.LOCATION));
    }

    public Coffee retrieveCoffee(URI coffee) {
        return client.target(coffee)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(Coffee.class);
    }

    private void verifySuccess(Response response) {
        if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL)
            throw new AssertionError("Status was not successful: " + response.getStatus());
    }

    public void close() {
        client.close();
    }

}
