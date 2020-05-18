package com.sebastian_daschner.coffee.entity;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class CoffeeBean {

    @Id
    public String name;

    @Relationship("IS_FROM")
    public Set<Origin> origins = new HashSet<>();

    @Property
    public Roast roast;

    @Relationship("TASTES")
    public Set<FlavorProfile> flavorProfiles = new HashSet<>();

    @Override
    public String toString() {
        return "CoffeeBean{" +
                "name='" + name + '\'' +
                ", origins=" + origins +
                ", roast=" + roast +
                ", flavorProfiles=" + flavorProfiles +
                '}';
    }
}
