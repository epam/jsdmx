package com.epam.jsdmx.serializer.common;

import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinition;

public class StubDataStructureLocalRepresentationAdapter implements TimeDimensionLocalRepresentationAdapter {
    public DataStructureDefinition adapt(DataStructureDefinition dsd) {
        return dsd;
    }
}
