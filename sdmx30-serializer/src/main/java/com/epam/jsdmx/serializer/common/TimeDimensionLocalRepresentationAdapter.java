package com.epam.jsdmx.serializer.common;

import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinition;

public interface TimeDimensionLocalRepresentationAdapter {
    DataStructureDefinition adapt(DataStructureDefinition dsd);
}