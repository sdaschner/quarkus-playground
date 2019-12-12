package com.sebastian_daschner.zero_downtime_k8s;

import javax.persistence.*;

@Entity
@Table(name = "coffees")
@NamedQuery(name = Coffee.FIND_ALL, query = "select c from Coffee c")
public class Coffee {

    public static final String FIND_ALL = "Coffee.findAll";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coffee_seq")
    @SequenceGenerator(name = "coffee_seq", allocationSize = 1)
    private long id;

    private String type;

    Coffee() {
    }

    public Coffee(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

}
