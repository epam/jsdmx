package com.epam.jsdmx.serializer.common;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.StructureClass;

public interface ReferenceAdapter {
    String adaptUrn(String urn);

    String toAdaptedUrn(ArtefactReference ref);

    default String adaptType(String type) {
        return type;
    }

    default StructureClass adaptType(StructureClass type) {
        return type;
    }
}
