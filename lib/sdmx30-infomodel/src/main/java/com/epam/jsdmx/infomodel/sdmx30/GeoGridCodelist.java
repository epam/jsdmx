package com.epam.jsdmx.infomodel.sdmx30;


import java.util.List;

/**
 * A code list that has defined a geographical grid composed of
 * cells representing regular squared portions of the Earth.
 */
public interface GeoGridCodelist extends GeoCodelist {
    /**
     * A regular expression string corresponding to the grid definition.
     */
    String getGridDefinition();

    @Override
    List<GridCode> getItems();

    @Override
    GeoGridCodelist toStub();

    @Override
    GeoGridCodelist toCompleteStub();

    @Override
    default GeoCodelistType getType() {
        return GeoCodelistType.GEO_GRID;
    }
}
