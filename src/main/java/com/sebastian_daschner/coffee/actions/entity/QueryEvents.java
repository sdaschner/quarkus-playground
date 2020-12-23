package com.sebastian_daschner.coffee.actions.entity;

import java.util.List;
import java.util.Map;

public class QueryEvents {

    public String query;
    public List<Map<String, ?>> events;

    public QueryEvents(String query, List<Map<String, ?>> events) {
        this.query = query;
        this.events = events;
    }

    public QueryEvents() {
    }
}
