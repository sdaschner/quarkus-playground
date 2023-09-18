package com.sebastian_daschner.client;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import jakarta.inject.Inject;
import picocli.CommandLine;

@TopCommand
@CommandLine.Command(name = "test")
public class TestCommand implements Runnable {

    @Inject
    SseClient client;

    @Override
    public void run() {
        client.someMethod();
    }

}
