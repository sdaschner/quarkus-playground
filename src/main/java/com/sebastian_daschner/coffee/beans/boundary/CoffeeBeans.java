package com.sebastian_daschner.coffee.beans.boundary;

import com.sebastian_daschner.coffee.beans.entity.*;
import org.neo4j.ogm.cypher.query.SortOrder;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.transaction.Transaction;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;

@ApplicationScoped
public class CoffeeBeans {

    @Inject
    SessionFactory sessionFactory;

    public CoffeeBean getCoffeeBean(String name) {
        Session session = sessionFactory.openSession();
        return session.load(CoffeeBean.class, name);
    }

    public List<CoffeeBean> getCoffeeBeans() {
        Session session = sessionFactory.openSession();
        return new ArrayList<>(session.loadAll(CoffeeBean.class, new SortOrder("name"), 1));
    }

    public List<CoffeeBean> getCoffeeBeansSpecificFlavor(String flavor) {
        Session session = sessionFactory.openSession();

        Iterable<CoffeeBean> result = session.query(CoffeeBean.class, "MATCH (b:CoffeeBean)-[:TASTES]->(:Flavor {name: $flavor})\n" +
                        "MATCH (b)-[isFrom:IS_FROM]->(country)\n" +
                        "MATCH (b)-[tastes:TASTES]->(flavor)\n" +
                        "RETURN b, collect(isFrom), collect(country), collect(tastes), collect(flavor)\n" +
                        "ORDER by b.name;",
                Map.of("flavor", flavor));

        return resultList(result);
    }

    public List<CoffeeBean> getCoffeeBeansWithUnexpectedFlavors() {
        Session session = sessionFactory.openSession();

        Iterable<CoffeeBean> result = session.query(CoffeeBean.class, "MATCH (flavor)<-[:TASTES]-(b:CoffeeBean)-[isFrom:IS_FROM]->(country)\n" +
                        "WHERE NOT (country)-[:IS_KNOWN_FOR]->(flavor)\n" +
                        "WITH b, country, isFrom\n" +
                        "MATCH (b)-[tastes:TASTES]->(flavor)\n" +
                        "RETURN b, collect(isFrom), collect(country), collect(tastes), collect(flavor)\n" +
                        "ORDER by b.name;",
                Map.of());

        return resultList(result);
    }

    private List<CoffeeBean> resultList(Iterable<CoffeeBean> result) {
        ArrayList<CoffeeBean> coffeeBeans = new ArrayList<>();
        result.forEach(coffeeBeans::add);
        return coffeeBeans;
    }

    public void createBean(String name, Roast roast, Set<String> origins, Map<String, Double> flavors) {
        Session session = sessionFactory.openSession();
        runInTransaction(() -> {

            CoffeeBean bean = new CoffeeBean();
            bean.name = name;
            bean.roast = roast;

            verifyNameNotExists(name, session);

            addOrigins(origins, session, bean);
            addFlavorProfiles(flavors, session, bean);

            verifyFlavorPercentages(bean);

            session.save(bean);

        }, session);
    }

    public UUID updateBeanFlavors(String name, Map<String, Double> flavors) {
        Session session = sessionFactory.openSession();

        return runInTransaction(() -> {
            CoffeeBean bean = session.load(CoffeeBean.class, name);
            if (bean == null)
                throw new IllegalArgumentException("Could not find bean with name " + name);

            bean.flavorProfiles.clear();
            addFlavorProfiles(flavors, session, bean);
            verifyFlavorPercentages(bean);

            session.save(bean);
        }, session);
    }

    public UUID deleteBean(String name) {
        Session session = sessionFactory.openSession();
        return runInTransaction(() -> session.delete(session.load(CoffeeBean.class, name)), session);
    }

    private void addOrigins(Set<String> origins, Session session, CoffeeBean bean) {
        origins.forEach(o -> {
            Origin origin = session.load(Origin.class, o);
            if (origin == null)
                throw new IllegalArgumentException("Origin with name " + o + " not found");

            bean.origins.add(origin);
        });
    }

    private void addFlavorProfiles(Map<String, Double> flavors, Session session, CoffeeBean bean) {
        flavors.forEach((f, p) -> {
            Flavor flavor = session.load(Flavor.class, f);
            if (flavor == null)
                throw new IllegalArgumentException("Flavor with " + f + " not found");

            FlavorProfile profile = new FlavorProfile(flavor, p);
            profile.bean = bean;
            bean.flavorProfiles.add(profile);
        });
    }

    private void verifyNameNotExists(String name, Session session) {
        CoffeeBean existing = session.load(CoffeeBean.class, name);
        if (existing != null)
            throw new IllegalArgumentException("Bean with name already exists");
    }

    private void verifyFlavorPercentages(CoffeeBean bean) {
        double sum = bean.flavorProfiles.stream()
                .mapToDouble(f -> f.percentage)
                .sum();
        if (Math.abs(1 - sum) >= 0.001)
            throw new IllegalArgumentException("Flavor percentages don't add up to 100% (1.0)");
    }

    private UUID runInTransaction(Runnable runnable, Session session) {
        Transaction transaction = session.beginTransaction();
        try {
            UUID actionId = UUID.randomUUID();
            runnable.run();
            session.query("CREATE (:Action {actionId: $actionId, timestamp: TIMESTAMP()})", Map.of("actionId", actionId));
            transaction.commit();
            transaction.close();
            return actionId;
        } catch (RuntimeException e) {
            System.err.println("Could not execute transaction: " + e);
            transaction.rollback();
            throw e;
        }
    }

}
