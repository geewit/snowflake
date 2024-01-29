package io.geewit.snowflake;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

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
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2024, Calendar.JANUARY, 29, 0, 0, 0);
        System.out.println(calendar1.getTimeInMillis());
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(65767, Calendar.MARCH, 13, 0, 0, 0);
        System.out.println(calendar2.getTimeInMillis());
        for (int i = 0; i < (1 << 2); i++) {
            long id = SnowFlake.ofDefault(48782507661878L).getUID();
            logger.info("cached({}).workerId({}): {}", String.format("%1$018d", 48782507661878L), String.format("%1$06d", i), id);
            if(ids.contains(id)) {
                logger.warn("cached.id({}) contains in set", id);
            } else {
                ids.add(id);
            }
            id = SnowFlake.ofDefault(238985134362842L).getUID();
            logger.info("default({}).workerId({}): {}", String.format("%1$018d", 238985134362842L), String.format("%1$06d", i), id);
            if(ids.contains(id)) {
                logger.warn("default.id({}) contains in set", id);
            } else {
                ids.add(id);
            }
            id = SnowFlake.ofDefault(160979938336661L).getUID();
            logger.info("default({}).workerId({}): {}", String.format("%1$018d", 160979938336661L), String.format("%1$06d", i), id);
            if(ids.contains(id)) {
                logger.warn("default.id({}) contains in set", id);
            } else {
                ids.add(id);
            }
            id = SnowFlake.ofDefault(258428451323233L).getUID();
            logger.info("default({}).workerId({}): {}", String.format("%1$018d", 258428451323233L), String.format("%1$06d", i), id);
            if(ids.contains(id)) {
                logger.warn("default.id({}) contains in set", id);
            } else {
                ids.add(id);
            }
            id = SnowFlake.ofDefault(213881050529453L).getUID();
            logger.info("default({}).workerId({}): {}", String.format("%1$018d", 213881050529453L), String.format("%1$06d", i), id);
            if(ids.contains(id)) {
                logger.warn("default.id({}) contains in set", id);
            } else {
                ids.add(id);
            }
            id = SnowFlake.ofDefault(259897330122789L).getUID();
            logger.info("default({}).workerId({}): {}", String.format("%1$018d", 259897330122789L), String.format("%1$06d", i), id);
            if(ids.contains(id)) {
                logger.warn("default.id({}) contains in set", id);
            } else {
                ids.add(id);
            }
            id = SnowFlake.ofDefault(116595746306610L).getUID();
            logger.info("default({}).workerId({}): {}", String.format("%1$018d", 116595746306610L), String.format("%1$06d", i), id);
            if(ids.contains(id)) {
                logger.warn("default.id({}) contains in set", id);
            } else {
                ids.add(id);
            }
        }
    }

    private static final long MAX_WORKER_ID = ~(-1L << 10);

    private static final long LOW_WORKER_ID = ~(-1L << (10 >> 1));

    private static final long HIGH_WORKER_ID = LOW_WORKER_ID ^ MAX_WORKER_ID;

    private long byteToLong(byte[] bytes) {
        long padToLong = (bytes[0] & 255);

        padToLong |= IntStream.range(1, Math.min(bytes.length, 8))
                .mapToLong(i -> ((long) bytes[i] & 255L) << i * 8)
                .reduce(0, (a, b) -> a | b);
        return padToLong;
    }

    private long entityId(String entityName) {
        Optional<MessageDigest> messageDigestOptional;
        try {
            messageDigestOptional = Optional.of(MessageDigest.getInstance("md5"));
        } catch (NoSuchAlgorithmException e) {
            messageDigestOptional = Optional.empty();
        }
        byte[] md5 = messageDigestOptional.get().digest(entityName.getBytes(StandardCharsets.UTF_8));
        long entityId = byteToLong(md5) & HIGH_WORKER_ID;
        return entityId;
    }

    @Test
    public void parseId() {
        long workerId = entityId("Route");
        logger.info("workerId: {}", workerId);
        long id = 8436992530134260000L;
        logger.info("id: {}, {}", id, SnowFlake.ofDefault(workerId).parseUID(id));
    }

    @Test
    public void defaultIdTest1() {
        long workerId = SnowFlake.ofWorkerId();
        logger.info("workerId: {}", workerId);
        IntStream.range(0, 10).forEach(i -> {
            long id = SnowFlake.ofDefault(workerId).getUID();
            logger.info("id({}): {}", String.format("%1$06d", i), id);
            if(ids.contains(id)) {
                logger.warn("cached.id({}) contains in set", id);
            } else {
                ids.add(id);
            }
        });
    }
}
