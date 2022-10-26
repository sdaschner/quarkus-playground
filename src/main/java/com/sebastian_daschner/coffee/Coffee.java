package com.sebastian_daschner.coffee;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "coffee_orders")
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
