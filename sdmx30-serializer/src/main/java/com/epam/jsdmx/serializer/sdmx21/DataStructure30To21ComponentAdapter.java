package com.epam.jsdmx.serializer.sdmx21;

import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinition;

public interface DataStructure30To21ComponentAdapter {
    DataStructureDefinition recompose(DataStructureDefinition dsd);
}
