package com.sebastian_daschner.entries;

import io.quarkus.qute.TemplateExtension;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class EntryFormatExtensions {

    private static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy", Locale.ENGLISH);
    private static final List<String> HEAD_REPLACEMENTS = Arrays.asList("<code>", "</code>");

    @TemplateExtension(matchName = "formattedDate")
    public static String formatDate(Entry entry) {
        return DATE_FORMATTER.format(entry.date).toLowerCase();
    }

    @TemplateExtension(matchName = "formattedHeadTitle")
    static String formatHeadTitle(Entry entry) {
        return HEAD_REPLACEMENTS.stream()
                .reduce(entry.title, (t, r) -> t.replace(r, ""));
    }

}
