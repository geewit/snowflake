package io.geewit.snowflake;

public class Test {
    public static void main(String[] args) {
        SnowFlake snowFlake = SnowFlake.build(2, 3);

        for (int i = 0; i < (1 << 12); i++) {
            System.out.println(snowFlake.nextId());
        }

    }
}
