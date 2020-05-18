package com.sebastian_daschner.coffee.boundary;

import com.sebastian_daschner.coffee.entity.CoffeeBean;
import org.neo4j.ogm.cypher.query.SortOrder;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class CoffeeBeans {

    @Inject
    SessionFactory sessionFactory;

    public List<CoffeeBean> getCoffeeBeans() {
        Session session = sessionFactory.openSession();
        return new ArrayList<>(session.loadAll(CoffeeBean.class, new SortOrder("name"), 1));
    }

    public List<CoffeeBean> getCoffeeBeansSpecificFlavor(String flavor) {
        Session session = sessionFactory.openSession();

        Iterable<CoffeeBean> result = session.query(CoffeeBean.class, "MATCH (b:CoffeeBean)-[:TASTES]->(:Flavor {description: $flavor})\n" +
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

}
