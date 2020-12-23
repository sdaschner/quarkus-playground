package com.sebastian_daschner.coffee.actions.entity;

import java.util.List;
import java.util.Set;

public class NodeSchemaMetadata {
    public List<Constraint> constraints;
    public List<String> identifierLabels;
    public List<String> labelsToAdd;
    public List<String> labelsToDelete;
    public Set<String> keys;

    public NodeSchemaMetadata(List<Constraint> constraints, List<String> identifierLabels, List<String> labelsToAdd, List<String> labelsToDelete, Set<String> keys) {
        this.identifierLabels = identifierLabels;
        this.constraints = constraints;
        this.labelsToAdd = labelsToAdd;
        this.labelsToDelete = labelsToDelete;
        this.keys = keys;
    }

    public NodeSchemaMetadata() {
    }
}
