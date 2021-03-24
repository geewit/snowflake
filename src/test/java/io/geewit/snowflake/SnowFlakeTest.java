package io.geewit.snowflake;


import org.junit.jupiter.api.Test;

public class SnowFlakeTest {
    @Test
    public void test() {
        for (int i = 0; i < (1 << 12); i++) {
            System.out.println(SnowFlake.ofCached(1L).getUID());
        }

        for (int i = 0; i < (1 << 12); i++) {
            System.out.println(SnowFlake.ofDefault(1L).getUID());
        }
    }
}
