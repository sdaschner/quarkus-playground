import com.sebastian_daschner.client.SseClient;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.sse.SseEventSource;
import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.LockSupport;

class SseClientTest {

    @Test
    void test() {
        Client client = ClientBuilder.newClient();

        WebTarget target = client.target("http://localhost:8080/updates");
        SseEventSource eventSource = SseEventSource.target(target).build();

        eventSource.register(ev -> {
            System.out.println("event: " + ev.getName() + " " + ev.readData());
        }, Throwable::printStackTrace);

        eventSource.open();

        System.out.println("opened");
        LockSupport.parkNanos(5_000_000_000L);

        eventSource.close();
        client.close();
    }

}
