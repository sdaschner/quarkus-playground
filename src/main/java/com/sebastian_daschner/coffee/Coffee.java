package com.sebastian_daschner.coffee;

import java.util.UUID;

public class Coffee {

    public UUID id;
    public String type;

    public Coffee() {
    }

    public Coffee(String type) {
        this.type = type;
    }

}
