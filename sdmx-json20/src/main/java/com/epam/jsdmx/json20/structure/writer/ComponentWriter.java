package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;

import com.epam.jsdmx.infomodel.sdmx30.Component;
import com.epam.jsdmx.infomodel.sdmx30.Representation;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;

import com.fasterxml.jackson.core.JsonGenerator;

public class ComponentWriter {

    private final IdentifiableWriter identifiableWriter;
    private final RepresentationWriter representationWriter;
    private final ReferenceAdapter referenceAdapter;

    public ComponentWriter(IdentifiableWriter identifiableWriter, RepresentationWriter representationWriter, ReferenceAdapter referenceAdapter) {
        this.identifiableWriter = identifiableWriter;
        this.representationWriter = representationWriter;
        this.referenceAdapter = referenceAdapter;
    }

    public void write(JsonGenerator jsonGenerator, Component component) throws IOException {
        identifiableWriter.write(jsonGenerator, component);
        if (component.getConceptIdentity() != null) {
            jsonGenerator.writeStringField(StructureUtils.CONCEPT_IDENTITY, referenceAdapter.toAdaptedUrn(component.getConceptIdentity()));
        }
        final Representation localRepresentation = component.getLocalRepresentation();
        if (localRepresentation != null) {
            representationWriter.writeRepresentation(jsonGenerator, localRepresentation, StructureUtils.LOCAL_REPRESENTATION);
        }
    }
}
