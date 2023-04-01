package com.epam.jsdmx.infomodel.sdmx30;

/**
 * A Code that has a geo feature set.
 */
public interface GeoFeatureSetCode extends GeoRefCode {
    /**
     * The geo feature set of the Code, which represents a set of points
     * defining a feature in a format defined a predefined pattern
     */
    String getValue();
}
