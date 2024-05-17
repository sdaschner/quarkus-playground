import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class System {

    private final Client client;
    private final WebTarget baseTarget;

    public System() {
        client = ClientBuilder.newBuilder().build();
        baseTarget = client.target(buildUri());
    }

    private URI buildUri() {
        String host = java.lang.System.getProperty("application.test.host", "localhost");
        String port = java.lang.System.getProperty("application.test.port", "8080");
        return UriBuilder.fromUri("http://{host}:{port}/hello").build(host, port);
    }

    public String sayHello() {
        Response response = baseTarget.request().get();
        verifySuccess(response);
        return response.readEntity(String.class);
    }

    public String sayHello(String name) {
        Response response = baseTarget.path(name).request().get();
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
