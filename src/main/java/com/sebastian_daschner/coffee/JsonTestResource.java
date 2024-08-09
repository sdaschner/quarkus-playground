package com.sebastian_daschner.coffee;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.annotation.JsonbTypeSerializer;
import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("json-test")
@ApplicationScoped
public class JsonTestResource {

    @GET
    public Schedule get() {
        Schedule schedule = new Schedule();
        schedule.name = "Name";

        Item item = new Item();
        item.name = "Item";
        item.schedule = schedule;
        schedule.item = item;

        return schedule;
    }

    public static class Schedule {

        public String name;

        // adding this makes it work
//        @JsonbTypeSerializer(ItemNameSerializer.class)
        public Item item;

    }

    public static class Item {

        public String name;

        @JsonbTypeSerializer(ScheduleNameSerializer.class)
        // adding @JsonbTransient also works
        public Schedule schedule;

    }

    public static class ScheduleNameSerializer implements JsonbSerializer<Schedule> {
        @Override
        public void serialize(Schedule schedule, JsonGenerator generator, SerializationContext ctx) {
            generator.write(schedule.name);
        }
    }

    public static class ItemNameSerializer implements JsonbSerializer<Item> {
        @Override
        public void serialize(Item item, JsonGenerator generator, SerializationContext ctx) {
            generator.write(item.name);
        }
    }

}
