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

@ApplicationScoped
@Path("updates")
@Produces(MediaType.SERVER_SENT_EVENTS)
//
// this class is required due to https://github.com/quarkusio/quarkus/issues/35967
// if this class gets removed, the SSE client connection stops working
public class DummyUpdatesResource {

    @Context
    Sse sse;

    @GET
    public void updates(@Context SseEventSink eventSink) {
        eventSink.send(createEvent());
    }

    private OutboundSseEvent createEvent() {
        return sse.newEventBuilder()
                .name("test")
                .data("test")
                .build();
    }

}
