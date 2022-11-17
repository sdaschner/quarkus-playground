package com.sebastian_daschner.ai;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("texts")
@ApplicationScoped
public class TextGenerationResource {

    @Inject
    OpenAIClient openAIClient;

    @GET
    public String text() {
        return openAIClient.generateText("Generate sentence with a quote by Leo Tolstoy");
    }

}
