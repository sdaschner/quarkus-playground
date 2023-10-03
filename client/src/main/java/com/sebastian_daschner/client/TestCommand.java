package com.sebastian_daschner.client;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import io.quarkus.runtime.Quarkus;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import picocli.CommandLine;

@TopCommand
@CommandLine.Command(name = "test")
public class TestCommand implements Runnable {

    @Inject
    SseClient client;

    @CommandLine.Option(names = "--client")
    boolean isClient;

    // run with java -Dquarkus.http.host-enabled=false -jar target/quarkus-app/quarkus-run.jar --client 
    @Override
    public void run() {
        if (isClient) {
            quarkus.http.port=0
            System.out.println("running test run()");
            client.someMethod();
        } else {
            Quarkus.waitForExit();
        }
    }

}
