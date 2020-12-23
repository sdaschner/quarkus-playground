package com.sebastian_daschner.coffee.actions.entity;

public class RelationshipPayload extends Payload {

    public String id;
    public RelationshipNodeChange start;
    public RelationshipNodeChange end;
    public RelationshipChange before;
    public RelationshipChange after;
    public String label;
    public EntityType type;

    public RelationshipPayload(String id, RelationshipNodeChange start, RelationshipNodeChange end, RelationshipChange before, RelationshipChange after, String label) {
        this.start = start;
        this.end = end;
        this.label = label;
        setId(id);
        setBefore(before);
        setAfter(after);
        setType(EntityType.relationship);
    }

    public RelationshipPayload() {
    }

    public void setId(String id) {
        this.id = id;
        super.id = id;
    }

    @Override
    public void setBefore(RecordChange before) {
        this.before = (RelationshipChange) before;
        super.setBefore(before);
    }

    @Override
    public void setAfter(RecordChange after) {
        this.after = (RelationshipChange) after;
        super.setAfter(after);
    }

    public void setType(EntityType type) {
        this.type = type;
        super.type = type;
    }
}
