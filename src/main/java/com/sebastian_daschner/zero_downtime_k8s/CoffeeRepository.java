package com.sebastian_daschner.zero_downtime_k8s;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CoffeeRepository implements PanacheRepository<Coffee> {

    //

}
