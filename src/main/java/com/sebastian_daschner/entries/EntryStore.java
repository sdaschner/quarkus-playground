package com.sebastian_daschner.entries;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class EntryStore {

    private List<Entry> entries;

    @PostConstruct
    void initEntries() {
        entries = createEntries();
    }

    public List<Entry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public Entry getEntry(String entryName) {
        return entries.stream()
                .filter(e -> entryName.equals(e.link))
                .findAny()
                .orElse(null);
    }

    private List<Entry> createEntries() {
        return List.of(
                new Entry("entry1", "This is an entry", LocalDateTime.now(), "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod"),
                new Entry("entry2", "This is another entry", LocalDateTime.now().minusDays(1), "<strong>Lorem</strong> tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At"),
                new Entry("entry3", "This is yet another entry", LocalDateTime.now().minusDays(2), "vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.")
        );
    }

}
