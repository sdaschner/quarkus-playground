package com.sebastian_daschner.entries;

import io.quarkus.qute.TemplateExtension;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@TemplateExtension
public class EntryFormatExtensions {

    private static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy", Locale.ENGLISH);

    public static String formattedDate(Entry entry) {
        return DATE_FORMATTER.format(entry.date).toLowerCase();
    }

}
