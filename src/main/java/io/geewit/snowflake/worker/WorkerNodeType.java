package io.geewit.snowflake.worker;

/**
 * WorkerNodeType
 *
 * @author geewit
 */
public enum WorkerNodeType {
    /**
     * Such as Docker
     */
    CONTAINER(1),
    /**
     * Actual machine
     */
    ACTUAL(2);

    /**
     * Lock type
     */
    private final Integer type;

    /**
     * Constructor with field of type
     */
    WorkerNodeType(Integer type) {
        this.type = type;
    }

    public int value() {
        return type;
    }

}
