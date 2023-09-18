package com.sebastian_daschner.client;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.sse.SseEventSource;

import java.util.concurrent.locks.LockSupport;

@ApplicationScoped
public class SseClient {

    private Client client;
    private SseEventSource updateSource;

    @PostConstruct
    void init() {
        client = ClientBuilder.newClient();
    }

    @PreDestroy
    void close() {
        client.close();
        if (updateSource != null)
            updateSource.close();
    }

    public void someMethod() {
        // [...]

        WebTarget target = client.target("http://localhost:8080/updates");
        updateSource = SseEventSource.target(target).build();
        updateSource.register(ev -> {
            System.out.println(ev.getName());
            System.out.println(ev.readData());
        }, thr -> {
            System.err.println("Error in SSE updates");
            thr.printStackTrace();
        });

        System.out.println("SSE opened");
        updateSource.open();

        LockSupport.parkNanos(5_000_000_000L);
    }

}