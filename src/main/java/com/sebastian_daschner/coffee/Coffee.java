package com.sebastian_daschner.coffee;

import java.util.UUID;

public class Coffee {

    public UUID id = UUID.randomUUID();
    public String type;

    Coffee() {
    }

    public Coffee(String type) {
        this.type = type;
    }
}
