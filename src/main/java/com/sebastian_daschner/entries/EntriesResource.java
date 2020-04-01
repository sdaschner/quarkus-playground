package com.sebastian_daschner.entries;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.ResourcePath;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@ApplicationScoped
@Path("entries")
@Produces(MediaType.TEXT_HTML)
public class EntriesResource {

    @Inject
    EntryStore entryStore;

    @ResourcePath("index.html")
    Template indexTemplate;

    @ResourcePath("entry.html")
    Template entryTemplate;

    @GET
    public TemplateInstance entries() {
        List<Entry> entries = entryStore.getEntries();
        return indexTemplate.data("entries", entries);
    }

    @GET
    @Path("{entry}")
    public TemplateInstance entry(@PathParam("entry") String entryName) {
        Entry entry = entryStore.getEntry(entryName);

        if (entry == null)
            throw new NotFoundException();

        return entryTemplate.data("entry", entry);
    }

}
