package com.sebastian_daschner.coffee;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
