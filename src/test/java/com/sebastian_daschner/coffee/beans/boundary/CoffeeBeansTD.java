package com.sebastian_daschner.coffee.beans.boundary;

import com.sebastian_daschner.coffee.graph.control.SessionFactoryProducer;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.SessionFactory;

public class CoffeeBeansTD extends CoffeeBeans {

    public CoffeeBeansTD() {
        String databaseUri = "bolt://localhost:7687";
        String username = "neo4j";
        String password = "test";

        Configuration neoConfig = new Configuration.Builder()
                .uri(databaseUri)
                .credentials(username, password)
                .useNativeTypes()
                .build();

        sessionFactory = new SessionFactory(neoConfig, SessionFactoryProducer.PACKAGES);
    }

}

