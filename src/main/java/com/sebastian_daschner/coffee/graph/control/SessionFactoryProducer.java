package com.sebastian_daschner.coffee.graph.control;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.SessionFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class SessionFactoryProducer {

    public static final String[] PACKAGES = {
            "com.sebastian_daschner.coffee.beans.entity",
            "com.sebastian_daschner.coffee.actions.entity"
    };

    @ConfigProperty(name = "quarkus.neo4j.uri")
    String databaseUri;

    @ConfigProperty(name = "quarkus.neo4j.authentication.username")
    String username;

    @ConfigProperty(name = "quarkus.neo4j.authentication.password")
    String password;

    @Produces
    SessionFactory produceSessionFactory() {
        Configuration neoConfig = new Configuration.Builder()
                .uri(databaseUri)
                .credentials(username, password)
                .useNativeTypes()
                .build();

        return new SessionFactory(neoConfig, PACKAGES);
    }

    void disposeSessionFactory(@Disposes SessionFactory sessionFactory) {
        sessionFactory.close();
    }

}
