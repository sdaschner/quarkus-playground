package com.sebastian_daschner.coffee.actions.entity;

import java.util.Map;

public abstract class RecordChange {
    public Map<String, Object> properties;

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
