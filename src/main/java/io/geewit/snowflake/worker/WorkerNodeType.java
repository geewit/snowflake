package io.geewit.snowflake.worker;


import io.geewit.core.utils.lang.enums.Value;

/**
 * WorkerNodeType
 * <li>CONTAINER: Such as Docker
 * <li>ACTUAL: Actual machine
 *
 * @author geewit
 */
public enum WorkerNodeType implements Value {

    CONTAINER(1), ACTUAL(2);

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

    @Override
    public int value() {
        return type;
    }

}
