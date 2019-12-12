package com.sebastian_daschner.zero_downtime_k8s;

import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.annotation.Metric;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class CoffeeShop {

    @Inject
    @Metric(name = "coffees_total")
    Counter totalCoffees;

    @Inject
    CoffeeRepository coffeeRepository;

    public String getCoffee() {
        totalCoffees.inc();
        return "Coffee";
    }

    @Bulkhead(value = 4, waitingTaskQueue = 4)
    @Asynchronous
    public CompletionStage<String> getAsyncCoffee() {
        totalCoffees.inc();
        return CompletableFuture.completedFuture("Coffee");
    }

    public long getCount() {
        return totalCoffees.getCount();
    }

    //

    @Transactional
    public List<Coffee> getCoffees() {
        return coffeeRepository.findAll().list();
    }

    @Transactional
    public void addCoffee(String type) {
        coffeeRepository.persist(new Coffee(type));
    }

}
