package com.sebastian_daschner.coffee.mp_cp;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class Notifier {

    @Inject
    ExampleStore exampleStore;

    public void notifyAbout() {
        System.out.println("trying to notify...");
        try {
            System.out.println("notified about new: " + exampleStore.getExample());
        } catch (Exception e) {
            System.err.println("got an error");
            e.printStackTrace();
        }
    }

}
