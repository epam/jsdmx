package com.epam.jsdmx.serializer.sdmx21;

import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.DataAttribute;
import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinition;

public interface MetadataConverter {
    List<DataAttribute> convertMetaAttributesToData(DataStructureDefinition dsd);
}
