package com.sebastian_daschner.coffee.beans.entity;

import org.neo4j.ogm.annotation.*;

import javax.json.bind.annotation.JsonbTransient;
import java.util.Objects;

@RelationshipEntity("TASTES")
public class FlavorProfile {

    @Id
    @GeneratedValue
    Long id;

    @StartNode
    @JsonbTransient
    public CoffeeBean bean;

    @EndNode
    public Flavor flavor;

    @Property
    public double percentage;

    private FlavorProfile() {
    }

    public FlavorProfile(Flavor flavor, double percentage) {
        this.flavor = flavor;
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "FlavorProfile{" +
                "flavor=" + flavor +
                ", percentage=" + percentage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlavorProfile that = (FlavorProfile) o;
        return Double.compare(that.percentage, percentage) == 0 &&
                Objects.equals(flavor, that.flavor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flavor, percentage);
    }

}
