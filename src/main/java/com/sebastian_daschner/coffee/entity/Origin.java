package com.sebastian_daschner.coffee.entity;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import javax.json.bind.annotation.JsonbTransient;
import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Origin {

    @Id
    public String country;

    @Relationship("IS_KNOWN_FOR")
    @JsonbTransient
    public Set<Flavor> knownForFlavors = new HashSet<>();

    @Override
    public String toString() {
        return "Origin{" +
                "country='" + country + '\'' +
                ", knownForFlavors=" + knownForFlavors +
                '}';
    }
}
