package io.geewit.snowflake;

import io.geewit.snowflake.impl.CachedUidGenerator;
import io.geewit.snowflake.impl.DefaultUidGenerator;


/**
 * twitter的snowflake算法 -- java实现
 *
 * @author geewit
 * @since 2019-02-19
 */
public class SnowFlake {

    private volatile static UidGenerator cachedUidGenerator;

    private volatile static UidGenerator defaultUidGenerator;

    public static UidGenerator ofCached(long workerId) {
        if (cachedUidGenerator == null) {
            synchronized (CachedUidGenerator.class) {
                if (cachedUidGenerator == null) {
                    cachedUidGenerator = new CachedUidGenerator(workerId);
                }
            }
        }
        return cachedUidGenerator;
    }

    public static UidGenerator ofDefault(long workerId) {
        if (defaultUidGenerator == null) {
            synchronized (DefaultUidGenerator.class) {
                if (defaultUidGenerator == null) {
                    defaultUidGenerator = new DefaultUidGenerator(workerId);
                }
            }
        }
        return defaultUidGenerator;
    }
}