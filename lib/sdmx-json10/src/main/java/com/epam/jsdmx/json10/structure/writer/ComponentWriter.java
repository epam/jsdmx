package com.epam.jsdmx.json10.structure.writer;

import java.io.IOException;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Component;
import com.epam.jsdmx.infomodel.sdmx30.Representation;
import com.epam.jsdmx.serializer.sdmx21.ReferenceResolver;

import com.fasterxml.jackson.core.JsonGenerator;

public class ComponentWriter {

    private final IdentifiableWriter identifiableWriter;
    private final RepresentationWriter representationWriter;
    private final ReferenceResolver referenceResolver;

    public ComponentWriter(IdentifiableWriter identifiableWriter,
                           RepresentationWriter representationWriter,
                           ReferenceResolver referenceResolver) {
        this.identifiableWriter = identifiableWriter;
        this.representationWriter = representationWriter;
        this.referenceResolver = referenceResolver;
    }

    public void write(JsonGenerator jsonGenerator, Component component) throws IOException {
        identifiableWriter.write(jsonGenerator, component);
        if (component.getConceptIdentity() != null) {
            final ArtefactReference ref = component.getConceptIdentity();
            jsonGenerator.writeStringField(StructureUtils.CONCEPT_IDENTITY, referenceResolver.resolve(ref).getUrn());
        }
        final Representation localRepresentation = component.getLocalRepresentation();
        if (localRepresentation != null) {
            representationWriter.writeRepresentation(jsonGenerator, localRepresentation, StructureUtils.LOCAL_REPRESENTATION);
        }
    }
}
