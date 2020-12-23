package com.sebastian_daschner.coffee.actions.entity;

import java.util.List;
import java.util.Set;

public class RelationshipSchemaMetadata {

    public String label;
    public List<String> startLabels;
    public List<String> endLabels;
    public Set<String> startKeys;
    public Set<String> endKeys;

    public RelationshipSchemaMetadata(String label, List<String> startLabels, List<String> endLabels, Set<String> startKeys, Set<String> endKeys) {
        this.label = label;
        this.startLabels = startLabels;
        this.endLabels = endLabels;
        this.startKeys = startKeys;
        this.endKeys = endKeys;
    }

    public RelationshipSchemaMetadata(RelationshipPayload payload) {
        this(payload.label,
                payload.start.labels != null ? payload.start.labels : List.of(),
                payload.end.labels != null ? payload.end.labels : List.of(),
                payload.start.ids.keySet(),
                payload.end.ids.keySet());
    }
}
