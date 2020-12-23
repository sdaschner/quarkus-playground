package com.sebastian_daschner.coffee.actions.entity;

public class NodePayload extends Payload {

    public String id;
    public NodeChange before;
    public NodeChange after;
    public EntityType type = EntityType.node;

    public NodePayload(String id, NodeChange before, NodeChange after, EntityType type) {
        setId(id);
        setBefore(before);
        setAfter(after);
        setType(type);
    }

    public NodePayload() {
    }

    public void setId(String id) {
        this.id = id;
        super.id = id;
    }

    @Override
    public void setBefore(RecordChange before) {
        this.before = (NodeChange) before;
        super.setBefore(before);
    }

    @Override
    public void setAfter(RecordChange after) {
        this.after = (NodeChange) after;
        super.setAfter(after);
    }

    public void setType(EntityType type) {
        this.type = type;
        super.type = type;
    }
}
