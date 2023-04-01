package com.epam.jsdmx.infomodel.sdmx30;

import java.time.Instant;

/**
 * A collection of values for the Member Selections that, combined with other Member
 * Selections, comprise the value content of the Cube Region.
 */
public interface SelectionValue {

    /**
     * @return Date from which the SelectionValue is valid.
     */
    Instant getValidFrom();

    /**
     * @return Date from which the SelectionValue is superseded.
     */
    Instant getValidTo();
}
