package com.sebastian_daschner.coffee.entity;

import com.sebastian_daschner.coffee.FlavorTypeSerializer;
import org.neo4j.ogm.annotation.Id;

import javax.json.bind.annotation.JsonbTypeSerializer;
import java.util.Objects;

/**
 * The Coffee flavors, following https://notbadcoffee.com/flavor-wheel-en.
 */
@JsonbTypeSerializer(FlavorTypeSerializer.class)
public class Flavor {

    @Id
    public String description;

    private Flavor() {
    }

    public Flavor(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flavor flavor = (Flavor) o;
        return Objects.equals(description, flavor.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

}
