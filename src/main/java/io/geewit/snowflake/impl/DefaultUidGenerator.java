package io.geewit.snowflake.impl;

import io.geewit.snowflake.BitsAllocator;
import io.geewit.snowflake.UidGenerator;
import io.geewit.snowflake.exception.UidGenerateException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Represents an implementation of {@link UidGenerator}
 * <p>
 * The unique id has 64bits (long), default allocated as blow:<br>
 * <li>sign: The highest bit is 0
 * <li>delta seconds: The next 28 bits, represents delta seconds since a customer epoch(2016-05-20 00:00:00.000).
 * Supports about 8.7 years until to 2024-11-20 21:24:16
 * <li>worker id: The next 22 bits, represents the worker's id which assigns based on database, max id is about 420W
 * <li>sequence: The next 13 bits, represents a sequence within the same second, max for 8192/s<br><br>
 * <p>
 * The {@link DefaultUidGenerator#parseUID(long)} is a tool method to parse the bits
 *
 * <pre>{@code
 * +------+----------------------+----------------+-----------+
 * | sign |     delta seconds    | worker node id | sequence  |
 * +------+----------------------+----------------+-----------+
 *   1bit          28bits              22bits         13bits
 * }</pre>
 * <p>
 * You can also specified the bits by Spring property setting.
 * <li>timeBits: default as 28
 * <li>workerBits: default as 22
 * <li>seqBits: default as 13
 * <li>epochStr: Epoch date string format 'yyyy-MM-dd'. Default as '2016-05-20'<p>
 *
 * <b>Note that:</b> The total bits must be 64 -1
 *
 * @author geewit
 */
public class DefaultUidGenerator implements UidGenerator {
    private static final Logger logger = LoggerFactory.getLogger(DefaultUidGenerator.class);

    private static final long EPOCH_MILLS = 1706457600000L;

    private static final long START_MILLS = System.currentTimeMillis();

    /**
     * Bits allocate
     */
    protected int timeBits = 41;
    protected int workerBits = 10;
    protected int seqBits = 12;

    /**
     * Customer epoch, unit as second. For example 2016-05-20 (ms: 1463673600000)
     */
    protected String epochStr = "2024-1-29 00:00:00";
    private final static Calendar startCalendar = Calendar.getInstance();
    static {
        startCalendar.set(65767, Calendar.MARCH, 13, 0, 22, 5);
    }
    protected final static long startSeconds = TimeUnit.MILLISECONDS.toSeconds(startCalendar.getTimeInMillis());


    private final static Calendar epochCalendar = Calendar.getInstance();
    static {
        epochCalendar.set(2024, Calendar.JANUARY, 29, 0, 0, 0);
    }

    protected long epochSeconds = TimeUnit.MILLISECONDS.toSeconds(epochCalendar.getTimeInMillis());

    private long shiftSeconds() {
        return startSeconds - epochSeconds;
    }

    /**
     * Stable fields after spring bean initializing
     */
    protected BitsAllocator bitsAllocator;
    protected long workerId;

    /**
     * Volatile fields caused by nextId()
     */
    protected long sequence = 0L;
    protected long lastSecond = -1L;

    public DefaultUidGenerator(long workerId) {
        // initialize bits allocator
        bitsAllocator = new BitsAllocator(timeBits, workerBits, seqBits);

        // initialize worker id

        this.workerId = workerId;
        if (this.workerId > bitsAllocator.getMaxWorkerId()) {
            this.workerId = this.workerId & bitsAllocator.getMaxWorkerId();
        }

        logger.info("Initialized bits(1, {}, {}, {}) for workerID:{}", this.timeBits, this.workerBits, this.seqBits, this.workerId);
    }

    @Override
    public long getUID() throws UidGenerateException {
        try {
            return this.nextId();
        } catch (Exception e) {
            logger.error("Generate unique id exception.", e);
            throw new UidGenerateException(e);
        }
    }

    @Override
    public String parseUID(long uid) {
        long totalBits = BitsAllocator.TOTAL_BITS;
        long signBits = bitsAllocator.getSignBits();
        long timestampBits = bitsAllocator.getTimestampBits();
        long workerIdBits = bitsAllocator.getWorkerIdBits();
        long sequenceBits = bitsAllocator.getSequenceBits();

        // parse UID
        long sequence = (uid << (totalBits - sequenceBits)) >>> (totalBits - sequenceBits);
        long workerId = (uid << (timestampBits + signBits)) >>> (totalBits - workerIdBits);
        long deltaSeconds = uid >>> (workerIdBits + sequenceBits);

        Date thatTime = new Date(TimeUnit.SECONDS.toMillis(epochSeconds + deltaSeconds - this.shiftSeconds()));
        String thatTimeStr = String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", thatTime);

        // format as string
        return String.format("{\"UID\":\"%d\",\"timestamp\":\"%s\",\"workerId\":\"%d\",\"sequence\":\"%d\"}",
                uid, thatTimeStr, workerId, sequence);
    }

    /**
     * Get UID
     *
     * @return UID
     * @throws UidGenerateException in the case: Clock moved backwards; Exceeds the max timestamp
     */
    protected synchronized long nextId() {
        long currentSecond = this.getCurrentSecond();

        // Clock moved backwards, refuse to generate uid
        if (currentSecond < lastSecond) {
            long refusedSeconds = lastSecond - currentSecond;
            throw new UidGenerateException("Clock moved backwards. Refusing for %d seconds", refusedSeconds);
        }

        // At the same second, increase sequence
        if (currentSecond == lastSecond) {
            sequence = (sequence + 1) & bitsAllocator.getMaxSequence();
            // Exceed the max sequence, we wait the next second to generate uid
            if (sequence == 0) {
                currentSecond = this.getNextSecond(lastSecond);
            }

            // At the different second, sequence restart from zero
        } else {
            sequence = 0L;
        }

        lastSecond = currentSecond;

        // Allocate bits for UID
        return bitsAllocator.allocate(currentSecond - epochSeconds + this.shiftSeconds(), workerId, sequence);
    }

    /**
     * Get next millisecond
     */
    private long getNextSecond(long lastTimestamp) {
        long timestamp = this.getCurrentSecond();
        while (timestamp <= lastTimestamp) {
            timestamp = this.getCurrentSecond();
        }

        return timestamp;
    }

    /**
     * Get current second
     */
    private long getCurrentSecond() {
        long currentTimeMillis = System.currentTimeMillis();
        long currentSecond;
        if (currentTimeMillis >= EPOCH_MILLS) {
            currentSecond = epochSeconds + (currentTimeMillis - EPOCH_MILLS) / 1000;
        } else {
            currentSecond = EPOCH_MILLS - (currentTimeMillis - START_MILLS) / 1000;
        }

        if (currentSecond - epochSeconds > bitsAllocator.getMaxDeltaSeconds()) {
            throw new UidGenerateException("Timestamp bits is exhausted. Refusing UID generate. Now: " + currentSecond);
        }

        return currentSecond;
    }

    public void setTimeBits(int timeBits) {
        if (timeBits > 0) {
            this.timeBits = timeBits;
        }
    }

    public void setWorkerBits(int workerBits) {
        if (workerBits > 0) {
            this.workerBits = workerBits;
        }
    }

    public void setSeqBits(int seqBits) {
        if (seqBits > 0) {
            this.seqBits = seqBits;
        }
    }

    public void setEpochStr(String epochStr) {
        if (StringUtils.isNotBlank(epochStr)) {
            this.epochStr = epochStr;
            Date epochDate;
            try {
                epochDate = DateUtils.parseDate(epochStr,  "yyyy-MM-dd HH:mm:ss");
                this.epochSeconds = TimeUnit.MILLISECONDS.toSeconds(epochDate.getTime());
            } catch (ParseException ignored) {
            }
        }
    }
}
