package com.sebastian_daschner.coffee.mp_cp;

import org.eclipse.microprofile.context.ManagedExecutor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
@Path("bulkheads")
public class BulkheadExampleResource {

    @Inject
    ExampleStore exampleStore;

    @Inject
    Notifier notifier;

    @Inject
    ManagedExecutor writeExecutor;

    @Inject
    ManagedExecutor readExecutor;

    @GET
    public CompletionStage<String> example() {
        return readExecutor.supplyAsync(exampleStore::getExample);
    }

    @PUT
    public CompletionStage<Void> setExample(String example) {
        return writeExecutor.runAsync(() -> {
            exampleStore.setExample(example);
            writeExecutor.execute(notifier::notifyAbout);
        });
    }

}
