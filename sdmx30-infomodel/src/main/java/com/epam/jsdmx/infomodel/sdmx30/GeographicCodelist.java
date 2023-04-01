package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * A special Codelist that has been extended to add a geographical feature
 * set to each of its items, typically, this would include all types of administrative
 * geographies.
 */
public interface GeographicCodelist extends GeoCodelist {
    @Override
    List<GeoFeatureSetCode> getItems();

    @Override
    GeographicCodelist toStub();

    @Override
    GeographicCodelist toCompleteStub();

    default GeoCodelistType getType() {
        return GeoCodelistType.GEOGRAPHIC;
    }
}
