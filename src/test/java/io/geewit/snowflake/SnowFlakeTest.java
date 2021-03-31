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
        for (int i = 0; i < (1 << 2); i++) {
            long id = SnowFlake.ofCached(48782507661878L).getUID();
            logger.info("cached({}).id({}): {}", String.format("%1$018d", 48782507661878L), String.format("%1$06d", i), id);
            if(ids.contains(id)) {
                logger.warn("cached.id({}) contains in set", id);
            } else {
                ids.add(id);
            }
            id = SnowFlake.ofCached(238985134362842L).getUID();
            logger.info("cached({}).id({}): {}", String.format("%1$018d", 238985134362842L), String.format("%1$06d", i), id);
            if(ids.contains(id)) {
                logger.warn("cached.id({}) contains in set", id);
            } else {
                ids.add(id);
            }
            id = SnowFlake.ofCached(160979938336661L).getUID();
            logger.info("cached({}).id({}): {}", String.format("%1$018d", 160979938336661L), String.format("%1$06d", i), id);
            if(ids.contains(id)) {
                logger.warn("cached.id({}) contains in set", id);
            } else {
                ids.add(id);
            }
            id = SnowFlake.ofCached(258428451323233L).getUID();
            logger.info("cached({}).id({}): {}", String.format("%1$018d", 258428451323233L), String.format("%1$06d", i), id);
            if(ids.contains(id)) {
                logger.warn("cached.id({}) contains in set", id);
            } else {
                ids.add(id);
            }
            id = SnowFlake.ofCached(213881050529453L).getUID();
            logger.info("cached({}).id({}): {}", String.format("%1$018d", 213881050529453L), String.format("%1$06d", i), id);
            if(ids.contains(id)) {
                logger.warn("cached.id({}) contains in set", id);
            } else {
                ids.add(id);
            }
            id = SnowFlake.ofCached(259897330122789L).getUID();
            logger.info("cached({}).id({}): {}", String.format("%1$018d", 259897330122789L), String.format("%1$06d", i), id);
            if(ids.contains(id)) {
                logger.warn("cached.id({}) contains in set", id);
            } else {
                ids.add(id);
            }
            id = SnowFlake.ofCached(116595746306610L).getUID();
            logger.info("cached({}).id({}): {}", String.format("%1$018d", 116595746306610L), String.format("%1$06d", i), id);
            if(ids.contains(id)) {
                logger.warn("cached.id({}) contains in set", id);
            } else {
                ids.add(id);
            }
        }
    }


    @Test
    public void defaultIdTest() {
        for (int i = 0; i < (1 << 2); i++) {
            long id = SnowFlake.ofDefault(48782507661878L).getUID();
            logger.info("cached({}).id({}): {}", String.format("%1$018d", 48782507661878L), String.format("%1$06d", i), id);
            if(ids.contains(id)) {
                logger.warn("cached.id({}) contains in set", id);
            } else {
                ids.add(id);
            }
            id = SnowFlake.ofDefault(238985134362842L).getUID();
            logger.info("cached({}).id({}): {}", String.format("%1$018d", 238985134362842L), String.format("%1$06d", i), id);
            if(ids.contains(id)) {
                logger.warn("cached.id({}) contains in set", id);
            } else {
                ids.add(id);
            }
            id = SnowFlake.ofDefault(160979938336661L).getUID();
            logger.info("cached({}).id({}): {}", String.format("%1$018d", 160979938336661L), String.format("%1$06d", i), id);
            if(ids.contains(id)) {
                logger.warn("cached.id({}) contains in set", id);
            } else {
                ids.add(id);
            }
            id = SnowFlake.ofDefault(258428451323233L).getUID();
            logger.info("cached({}).id({}): {}", String.format("%1$018d", 258428451323233L), String.format("%1$06d", i), id);
            if(ids.contains(id)) {
                logger.warn("cached.id({}) contains in set", id);
            } else {
                ids.add(id);
            }
            id = SnowFlake.ofDefault(213881050529453L).getUID();
            logger.info("cached({}).id({}): {}", String.format("%1$018d", 213881050529453L), String.format("%1$06d", i), id);
            if(ids.contains(id)) {
                logger.warn("cached.id({}) contains in set", id);
            } else {
                ids.add(id);
            }
            id = SnowFlake.ofDefault(259897330122789L).getUID();
            logger.info("cached({}).id({}): {}", String.format("%1$018d", 259897330122789L), String.format("%1$06d", i), id);
            if(ids.contains(id)) {
                logger.warn("cached.id({}) contains in set", id);
            } else {
                ids.add(id);
            }
            id = SnowFlake.ofDefault(116595746306610L).getUID();
            logger.info("cached({}).id({}): {}", String.format("%1$018d", 116595746306610L), String.format("%1$06d", i), id);
            if(ids.contains(id)) {
                logger.warn("cached.id({}) contains in set", id);
            } else {
                ids.add(id);
            }
        }
    }
}
