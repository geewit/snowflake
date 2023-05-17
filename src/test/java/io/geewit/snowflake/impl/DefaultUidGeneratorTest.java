package io.geewit.snowflake.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DefaultUidGeneratorTest {

    @Test
    public void epochStrTest() {
        long epochSeconds = TimeUnit.MILLISECONDS.toSeconds(1645545600000L);
        System.out.println(epochSeconds);
        String epochStr = "2022-02-23";
        if (StringUtils.isNotBlank(epochStr)) {
            Date epochDate;
            try {
                epochDate = DateUtils.parseDate(epochStr,  "yyyy-MM-dd");
                epochSeconds = TimeUnit.MILLISECONDS.toSeconds(epochDate.getTime());
                System.out.println(epochSeconds);
                System.out.println(22 >> 1);
                final long MAX_WORKER_ID = ~(-1L << 22);
                System.out.println(MAX_WORKER_ID);
                final long LOW_WORKER_ID = ~(-1L << (22 >> 1));
                System.out.println(LOW_WORKER_ID);
                System.out.println(LOW_WORKER_ID ^ MAX_WORKER_ID);

                System.out.println(2047 |  4192256);
            } catch (ParseException ignored) {
            }
        }
    }
}
