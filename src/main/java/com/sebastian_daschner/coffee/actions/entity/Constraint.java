package com.sebastian_daschner.coffee.actions.entity;

import java.util.Set;

public class Constraint {

    public String label;
    public Set<String> properties;
    public StreamsConstraintType type;

    public enum StreamsConstraintType {UNIQUE, NODE_PROPERTY_EXISTS, RELATIONSHIP_PROPERTY_EXISTS}
}
