package com.sebastian_daschner.coffee.actions.entity;

import java.util.Map;

public class RelationshipChange extends RecordChange {
    public Map<String, Object> properties;

    @Override
    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
        super.setProperties(properties);
    }
}
