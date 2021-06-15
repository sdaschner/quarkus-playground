import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
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
        return UriBuilder.fromUri("http://{host}:{port}/coffee/").build(host, port);
    }

    public String getCoffee() {
        Response response = baseTarget.request().get();
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
