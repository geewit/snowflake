package io.geewit.snowflake.buffer;

import java.util.List;

/**
 * Buffered UID provider(Lambda supported), which provides UID in the same one second
 *
 * @author geewit
 */
@FunctionalInterface
public interface BufferedUidProvider {

    /**
     * Provides UID in one second
     *
     * @param momentInSecond
     * @return UID in one second
     */
    List<Long> provide(long momentInSecond);
}
