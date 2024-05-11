package org.jhipster.quickjoin.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ClassesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Classes getClassesSample1() {
        return new Classes()
            .id(1L)
            .title("title1")
            .description("description1")
            .techer_name("techer_name1")
            .price(1)
            .location("location1")
            .duration(1);
    }

    public static Classes getClassesSample2() {
        return new Classes()
            .id(2L)
            .title("title2")
            .description("description2")
            .techer_name("techer_name2")
            .price(2)
            .location("location2")
            .duration(2);
    }

    public static Classes getClassesRandomSampleGenerator() {
        return new Classes()
            .id(longCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .techer_name(UUID.randomUUID().toString())
            .price(intCount.incrementAndGet())
            .location(UUID.randomUUID().toString())
            .duration(intCount.incrementAndGet());
    }
}
