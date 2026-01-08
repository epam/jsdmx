package com.epam.jsdmx.serializer.sdmx30.common;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;

public class DefaultReferenceAdapter implements ReferenceAdapter {
    @Override
    public String adaptUrn(String urn) {
        return urn;
    }

    @Override
    public String toAdaptedUrn(ArtefactReference ref) {
        return ref != null ? ref.getUrn() : null;
    }
}
