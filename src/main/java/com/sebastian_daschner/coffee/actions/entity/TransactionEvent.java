package com.sebastian_daschner.coffee.actions.entity;

public class TransactionEvent {

    public Meta meta;
    public Payload payload;
    public Schema schema;

    public TransactionEvent(Meta meta, Payload payload, Schema schema) {
        this.meta = meta;
        this.schema = schema;
        this.payload = payload;
    }

    public TransactionEvent() {
    }

    public void swapBeforeAfterProperties() {
        RecordChange before = payload.before;
        RecordChange after = payload.after;
        payload.setBefore(after);
        payload.setAfter(before);
    }

}
