package com.epam.jsdmx.infomodel.sdmx30;

/**
 * A GeoCodelist is a specialisation of Codelist that includes geospatial information,
 * by comprising a set of special Codes, i.e., GeoRefCodes
 */
public interface GeoCodelist extends Codelist {
    /**
     * The type of Geo Codelist that the Codelist will become.
     */
    GeoCodelistType getType();
}
