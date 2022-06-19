package com.sebastian_daschner.coffee;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.time.Instant;
import java.util.UUID;

@Entity
public class Coffee {

    @Id
    @GeneratedValue
    public UUID id;
    public String type;
    public Instant created;

    public Coffee() {
    }

    public Coffee(String type) {
        this.type = type;
    }

    @PrePersist
    void updateCreated() {
        created = Instant.now();
    }
}
