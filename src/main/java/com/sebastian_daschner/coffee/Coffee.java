package com.sebastian_daschner.coffee;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name = "coffees")
public class Coffee extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coffee_seq")
    @SequenceGenerator(name = "coffee_seq", allocationSize = 1)
    public long id;

    public String type;

    Coffee() {
    }

    public Coffee(String type) {
        this.type = type;
    }
}
