package com.sebastian_daschner.cars;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue
    public long id;

    public String color;

    @Embedded
    public Tire tireType;

}
