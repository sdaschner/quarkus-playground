package com.sebastian_daschner.entries;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.ResourcePath;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.util.List;

@Path("entries")
@ApplicationScoped
@Produces(MediaType.TEXT_HTML)
public class EntriesResource {

    private List<Entry> entries;

    @ResourcePath("index.html")
    Template entriesTemplate;

    @ResourcePath("entry.html")
    Template entryTemplate;

    @PostConstruct
    void initEntries() {
        entries = createEntries();
    }

    @GET
    public TemplateInstance entries() {
        return entriesTemplate.data("entries", entries);
    }

    @GET
    @Path("{entry}")
    public TemplateInstance entry(@PathParam("entry") String entryName) {
        Entry entry = entries.stream()
                .filter(e -> entryName.equals(e.link))
                .findAny()
                .orElseThrow(NotFoundException::new);

        return entryTemplate.data("entry", entry);
    }

    private List<Entry> createEntries() {
        return List.of(
                new Entry("entry1", "This is an entry", LocalDateTime.now(), "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod"),
                new Entry("entry2", "This is another entry", LocalDateTime.now().minusDays(1), "tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At"),
                new Entry("entry3", "This is yet another entry", LocalDateTime.now().minusDays(2), "vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.")
        );
    }

}
