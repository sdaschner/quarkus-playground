package com.sebastian_daschner.coffee.actions.entity;

import java.util.Map;

public class Meta {
    public Long timestamp;
    public String username;
    public Long txId;
    public int txEventId;
    public int txEventsCount;
    public OperationType operation;
    public Map<String, ?> source;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getTxId() {
        return txId;
    }

    public void setTxId(Long txId) {
        this.txId = txId;
    }

    public int getTxEventId() {
        return txEventId;
    }

    public void setTxEventId(int txEventId) {
        this.txEventId = txEventId;
    }

    public int getTxEventsCount() {
        return txEventsCount;
    }

    public void setTxEventsCount(int txEventsCount) {
        this.txEventsCount = txEventsCount;
    }

    public OperationType getOperation() {
        return operation;
    }

    public void setOperation(OperationType operation) {
        this.operation = operation;
    }

    public Map<String, ?> getSource() {
        return source;
    }

    public void setSource(Map<String, ?> source) {
        this.source = source;
    }
}
