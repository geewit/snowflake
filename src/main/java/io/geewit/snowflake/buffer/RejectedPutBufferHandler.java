package io.geewit.snowflake.buffer;

/**
 * If tail catches the cursor it means that the ring buffer is full, any more buffer put request will be rejected.
 * Specify the policy to handle the reject. This is a Lambda supported interface
 *
 * @author geewit
 */
@FunctionalInterface
public interface RejectedPutBufferHandler {

    /**
     * Reject put buffer request
     *
     * @param ringBuffer ringBuffer
     * @param uid uid
     */
    void rejectPutBuffer(RingBuffer ringBuffer, long uid);
}
