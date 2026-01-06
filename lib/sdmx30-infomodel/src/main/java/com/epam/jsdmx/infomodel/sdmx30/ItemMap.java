package com.epam.jsdmx.infomodel.sdmx30;

import java.time.Instant;

/**
 * Describes how the source value maps to the target value
 */
public interface ItemMap extends AnnotableArtefact, Copyable {
    /**
     * Input value for source.
     */
    String getSource();

    /**
     * Output value for each mapped target.
     */
    String getTarget();

    /**
     * Optional period describing when the mapping is applicable.
     */
    Instant getValidFrom();

    /**
     * Optional period describing which the mapping is no longer applicable.
     */
    Instant getValidTo();

    /**
     * If true, the sourceValue field should be treated as a regular expression when comparing with the source data.
     */
    boolean isRegEx();

    /**
     * If provided, a substring of the source data should be taken,
     * starting from this index (starting at zero) before comparing with the value field for matching.
     */
    int getStartIndex();

    /**
     * If provided, a substring of the source data should be taken, ending at this index (starting at zero) before comparing with the value field for matching.
     */
    int getEndIndex();
}
