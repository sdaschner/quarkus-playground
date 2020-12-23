package com.sebastian_daschner.coffee.actions.entity;

import java.util.List;
import java.util.Map;

public class NodeChange extends RecordChange {
    public Map<String, Object> properties;
    public List<String> labels;

    @Override
    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
        super.setProperties(properties);
    }
}
