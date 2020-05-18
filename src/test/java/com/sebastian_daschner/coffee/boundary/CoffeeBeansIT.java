package com.sebastian_daschner.coffee.boundary;

import com.sebastian_daschner.coffee.entity.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CoffeeBeansIT {

    private final CoffeeBeans coffeeBeans = new CoffeeBeansTD();

    @Test
    void testGetCoffeeBeans() {
        List<CoffeeBean> beans = coffeeBeans.getCoffeeBeans();
        assertThat(beans).hasSize(4);

        CoffeeBean bean = beans.get(0);
        assertThat(bean.name).isEqualTo("Buna");
        assertThat(bean.roast).isEqualTo(Roast.LIGHT);
        assertThat(bean.origins).hasSize(1);
        Origin origin = bean.origins.iterator().next();
        assertThat(origin.country).isEqualTo("Ethiopia");
        assertThat(bean.flavorProfiles).containsExactlyInAnyOrder(
                new FlavorProfile(new Flavor("FRUIT"), 0.4),
                new FlavorProfile(new Flavor("CARAMEL"), 0.3),
                new FlavorProfile(new Flavor("SWEET"), 0.3));

        bean = beans.get(1);
        assertThat(bean.name).isEqualTo("El gato loco");
        assertThat(bean.roast).isEqualTo(Roast.LIGHT);
        assertThat(bean.origins).hasSize(1);
        origin = bean.origins.iterator().next();
        assertThat(origin.country).isEqualTo("Colombia");
        assertThat(bean.flavorProfiles).containsExactlyInAnyOrder(
                new FlavorProfile(new Flavor("FRUIT"), 0.7),
                new FlavorProfile(new Flavor("FLORAL"), 0.3));

        bean = beans.get(2);
        assertThat(bean.name).isEqualTo("Kahawa Nzuri");
        assertThat(bean.roast).isEqualTo(Roast.MEDIUM);
        assertThat(bean.origins).hasSize(1);
        origin = bean.origins.iterator().next();
        assertThat(origin.country).isEqualTo("Kenya");
        assertThat(bean.flavorProfiles).containsExactlyInAnyOrder(
                new FlavorProfile(new Flavor("CHOCOLATE"), 0.5),
                new FlavorProfile(new Flavor("CARAMEL"), 0.5));

        bean = beans.get(3);
        assertThat(bean.name).isEqualTo("Saboroso");
        assertThat(bean.roast).isEqualTo(Roast.MEDIUM);
        assertThat(bean.origins).hasSize(1);
        origin = bean.origins.iterator().next();
        assertThat(origin.country).isEqualTo("Brazil");
        assertThat(bean.flavorProfiles).containsExactlyInAnyOrder(
                new FlavorProfile(new Flavor("CHOCOLATE"), 0.5),
                new FlavorProfile(new Flavor("NUTTY"), 0.3),
                new FlavorProfile(new Flavor("CARAMEL"), 0.2));
    }

    @Test
    void testGetCoffeeBeansSpecificFlavor() {
        List<CoffeeBean> beans = coffeeBeans.getCoffeeBeansSpecificFlavor("FRUIT");
        assertThat(beans).hasSize(2);
        assertThat(beans).extracting(b -> b.name).containsExactly("Buna", "El gato loco");

        beans = coffeeBeans.getCoffeeBeansSpecificFlavor("SWEET");
        assertThat(beans).hasSize(1);
        assertThat(beans).extracting(b -> b.name).containsExactly("Buna");

        beans = coffeeBeans.getCoffeeBeansSpecificFlavor("CARAMEL");
        assertThat(beans).hasSize(3);
        assertThat(beans).extracting(b -> b.name).containsExactly("Buna", "Kahawa Nzuri", "Saboroso");
    }

    @Test
    void testGetCoffeeBeansWithUnexpectedFlavors() {
        List<CoffeeBean> beans = coffeeBeans.getCoffeeBeansWithUnexpectedFlavors();
        assertThat(beans).hasSize(2);

        // test that properties are available
        CoffeeBean bean = beans.get(0);
        assertThat(bean.name).isEqualTo("Buna");
        assertThat(bean.roast).isEqualTo(Roast.LIGHT);
        assertThat(bean.origins).hasSize(1);
        Origin origin = bean.origins.iterator().next();
        assertThat(origin.country).isEqualTo("Ethiopia");
        assertThat(bean.flavorProfiles).containsExactlyInAnyOrder(
                new FlavorProfile(new Flavor("FRUIT"), 0.4),
                new FlavorProfile(new Flavor("CARAMEL"), 0.3),
                new FlavorProfile(new Flavor("SWEET"), 0.3));

        bean = beans.get(1);
        assertThat(bean.name).isEqualTo("Saboroso");
        assertThat(bean.roast).isEqualTo(Roast.MEDIUM);
        assertThat(bean.origins).hasSize(1);
        origin = bean.origins.iterator().next();
        assertThat(origin.country).isEqualTo("Brazil");
        assertThat(bean.flavorProfiles).containsExactlyInAnyOrder(
                new FlavorProfile(new Flavor("CHOCOLATE"), 0.5),
                new FlavorProfile(new Flavor("NUTTY"), 0.3),
                new FlavorProfile(new Flavor("CARAMEL"), 0.2));
    }

}