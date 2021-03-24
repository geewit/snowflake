package io.geewit.snowflake;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class SnowFlakeTest {
    private static final Logger logger = LoggerFactory.getLogger(SnowFlakeTest.class);

    private Set<Long> ids = new HashSet<>();

    @Test
    public void cachedIdTest() {
        for (int i = 0; i < (1 << 12); i++) {
            long id = SnowFlake.ofCached(1L).getUID();
            logger.info("cached.id({}): {}", String.format("%1$06d", i), id);
            if(ids.contains(id)) {
                logger.warn("cached.id({}) contains in set", id);
            } else {
                ids.add(id);
            }
        }
    }


    @Test
    public void defaultIdTest() {
        for (int i = 0; i < (1 << 12); i++) {
            long id = SnowFlake.ofDefault(1L).getUID();
            logger.info("default.id({}): {}", String.format("%1$06d", i), id);
            if(ids.contains(id)) {
                logger.warn("default.id({}) contains in set", id);
            } else {
                ids.add(id);
            }
        }
    }
}
