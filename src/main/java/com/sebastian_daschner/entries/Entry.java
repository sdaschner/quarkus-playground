package com.sebastian_daschner.entries;

import java.time.LocalDateTime;

public class Entry {

    public String link;
    public String title;
    public LocalDateTime date;
    public String abstractContent;
    public String content;

    public Entry(String link, String title, LocalDateTime date, String abstractContent) {
        this.link = link;
        this.title = title;
        this.date = date;
        this.abstractContent = abstractContent;
        this.content = abstractContent;
    }
}
