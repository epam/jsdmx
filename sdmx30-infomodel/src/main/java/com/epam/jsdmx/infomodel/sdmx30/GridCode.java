package com.epam.jsdmx.infomodel.sdmx30;

/**
 * A Code that represents a Geo Grid Cell belonging in a specific grid definition.
 */
public interface GridCode extends GeoRefCode {
    /**
     * The value used to assign the Code to one cell in the grid
     */
    String getGeoCell();

}
