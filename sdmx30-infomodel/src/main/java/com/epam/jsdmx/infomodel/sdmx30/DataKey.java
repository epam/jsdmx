package com.epam.jsdmx.infomodel.sdmx30;

import java.time.Instant;
import java.util.List;

/**
 * The values of a key in a data set.
 */
public interface DataKey extends Copyable {
    /**
     * Indicates whether the Data Key Set is included in the constraint definition or excluded from the constraint definition.
     */
    boolean isIncluded();

    /**
     * Associates the Component Values that comprise the key
     */
    List<ComponentValue> getKeyValues();

    /**
     * Date from which the Data Key is valid.
     */
    Instant getValidFrom();

    /**
     * Date from which the Data Key is superseded.
     */
    Instant getValidTo();

    @Override
    DataKey clone();
}
