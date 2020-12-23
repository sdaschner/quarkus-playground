package com.sebastian_daschner.coffee.beans.entity;

import com.sebastian_daschner.coffee.beans.FlavorTypeSerializer;
import org.neo4j.ogm.annotation.Id;

import javax.json.bind.annotation.JsonbTypeSerializer;
import java.util.Objects;

/**
 * The Coffee flavors, following https://notbadcoffee.com/flavor-wheel-en.
 */
@JsonbTypeSerializer(FlavorTypeSerializer.class)
public class Flavor {

    @Id
    public String name;

    private Flavor() {
    }

    public Flavor(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flavor flavor = (Flavor) o;
        return Objects.equals(name, flavor.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
