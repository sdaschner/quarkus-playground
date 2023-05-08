package com.sebastian_daschner.coffee;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "coffee_orders")
public class Coffee {

    @Id
    @GeneratedValue
    public UUID id;
    public String type;

    public Coffee() {
    }

    public Coffee(String type) {
        this.type = type;
    }

}
