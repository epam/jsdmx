package com.epam.jsdmx.serializer.sdmx30.structure;


import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.serializer.sdmx30.common.DataLocation;

public interface StructureReader {

    Artefacts read(DataLocation location);

}
