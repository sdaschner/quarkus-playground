package com.sebastian_daschner.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.sse.OutboundSseEvent;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseEventSink;

import java.util.concurrent.locks.LockSupport;

@ApplicationScoped
@Path("updates")
@Produces(MediaType.SERVER_SENT_EVENTS)
//
// this class is required due to https://github.com/quarkusio/quarkus/issues/35967 (only when quarkus-rest-client-jsonb is used, not for -reactive)
// if this class gets removed, the SSE client connection stops working
public class UpdatesResource {

    @Context
    Sse sse;

    @GET
    public void updates(@Context SseEventSink eventSink) {
        eventSink.send(createEvent("test234", "test"));
        LockSupport.parkNanos(1_000_000_000L);
        eventSink.send(createEvent("test123", ""));
        LockSupport.parkNanos(1_000_000_000L);
        eventSink.send(createEvent("test234", "test"));
        LockSupport.parkNanos(1_000_000_000L);
        eventSink.send(createEvent("test123", ""));
    }

    private OutboundSseEvent createEvent(String name, String data) {
        return sse.newEventBuilder()
                .name(name)
                .data(data)
                .build();
    }

}
