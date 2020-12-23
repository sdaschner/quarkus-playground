package com.sebastian_daschner.coffee.actions.entity;

public abstract class Payload {
    public String id;
    public EntityType type;
    public RecordChange before;
    public RecordChange after;

    public void setBefore(RecordChange before) {
        this.before = before;
    }

    public void setAfter(RecordChange after) {
        this.after = after;
    }
}
