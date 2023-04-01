package com.epam.jsdmx.infomodel.sdmx30;

/**
 * Describes an input value
 */
public interface MappedValue {

    /**
     * @return The value to compare the source data with.
     */
    String getValue();

    /**
     * @return If provided a substring of the source data should be taken, starting from this
     * index (starting at zero) before comparing with the value field for matching
     */
    int getStartIndex();

    /**
     * @return If provided a substring of the source data should be taken, ending at this index
     * (starting at zero) before comparing with the value field for matching
     */
    int getEndIndex();

    /**
     * @return If true the value field should be treated as a regular expression when
     * comparing with the source data
     */
    boolean isRegEx();
}
