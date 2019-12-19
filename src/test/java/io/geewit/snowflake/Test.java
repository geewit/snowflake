package io.geewit.snowflake;

public class Test {
    public static void main(String[] args) {

        for (int i = 0; i < (1 << 12); i++) {
            System.out.println(SnowFlake.ofCached(1L).getUID());
        }

        for (int i = 0; i < (1 << 12); i++) {
            System.out.println(SnowFlake.ofDefault(1L).getUID());
        }
    }
}
