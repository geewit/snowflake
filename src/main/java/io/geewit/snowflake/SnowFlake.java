package io.geewit.snowflake;

import io.geewit.snowflake.impl.CachedUidGenerator;
import io.geewit.snowflake.impl.DefaultUidGenerator;

import java.net.InetAddress;
import java.net.UnknownHostException;


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

    public static long ofWorkerId() {
        InetAddress localHost;
        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        String ip = localHost.getHostAddress();
        String[] parts = ip.split("\\.");
        int last8Bits = Integer.parseInt(parts[3]);
        int last16Bits = Integer.parseInt(parts[2]); // shift left 8 bits and combine with the last 8 bits
        return (((long) last16Bits << 8) | last8Bits) & ((1 << 10) - 1); // shift the first 2 bits left by 8 and combine with the last 8 bits
    }
}