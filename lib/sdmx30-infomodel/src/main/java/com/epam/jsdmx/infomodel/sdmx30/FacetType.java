package com.epam.jsdmx.infomodel.sdmx30;

/**
 * The FacetType enumeration is used to specify the valid format of
 * the content of a non-enumerated Concept or the usage of a Concept when specified for use
 * on a Component on a Structure (such as a Dimension in a DataStructureDefinition).
 */
public enum FacetType {
    /**
     * Indicates a sequentially increasing value
     */
    IS_SEQUENCE,

    /**
     * For specifying text can occur in more than one language
     */
    IS_MULTILINGUAL,

    /**
     * A number of characters or digits
     */
    MIN_LENGTH,

    /**
     * A number of characters or digits
     */
    MAX_LENGTH,

    /**
     * A minimum value for numeric range
     */
    MIN_VALUE,

    /**
     * A maximum value for numeric range
     */
    MAX_VALUE,

    /**
     * Start value of a numeric sequence
     */
    START_VALUE,

    /**
     * End value of a numeric sequence
     */
    END_VALUE,

    /**
     * Interval between values of a numeric sequence
     */
    INTERVAL,

    /**
     * Duration of a time interval
     */
    TIME_INTERVAL,

    /**
     * A number of digits after the decimal point
     */
    DECIMALS,

    /**
     * A regular expression, as per W3C XML Schema
     */
    PATTERN,

    /**
     * Start of a time range
     */
    START_TIME,

    /**
     * End of a time range
     */
    END_TIME,

    /**
     * A collection of sentinel values
     */
    SENTINEL_VALUES
}
