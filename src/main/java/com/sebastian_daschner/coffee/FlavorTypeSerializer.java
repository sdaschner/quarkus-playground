package com.sebastian_daschner.coffee;

import com.sebastian_daschner.coffee.entity.Flavor;

import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;

public class FlavorTypeSerializer implements JsonbSerializer<Flavor> {

    @Override
    public void serialize(Flavor flavor, JsonGenerator generator, SerializationContext ctx) {
        generator.write(flavor.description);
    }

}
