package com.epam.jsdmx.json10.structure.writer;

import java.io.IOException;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.serializer.sdmx21.ReferenceResolver;

import com.fasterxml.jackson.core.JsonGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConceptRoleWriter {

    private final ReferenceResolver referenceResolver;

    public void write(JsonGenerator jsonGenerator, List<ArtefactReference> conceptRoles) throws IOException {
        jsonGenerator.writeFieldName(StructureUtils.CONCEPT_ROLES);
        jsonGenerator.writeStartArray();
        if (conceptRoles != null && !conceptRoles.isEmpty()) {
            for (ArtefactReference conceptRef : conceptRoles) {
                jsonGenerator.writeString(referenceResolver.resolve(conceptRef).getUrn());
            }
        }
        jsonGenerator.writeEndArray();
    }
}
