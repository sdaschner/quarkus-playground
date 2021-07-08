package com.sebastian_daschner.coffee;

import io.smallrye.config.ConfigMapping;

import java.util.List;

@ConfigMapping(prefix = "complex")
public interface ComplexConfiguration {

    String coffee();
    List<String> list();

    SomeOther someOther();

    interface SomeOther {
        String coffee();
        List<String> list();
    }
}
