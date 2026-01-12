package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;

import com.fasterxml.jackson.core.JsonGenerator;

public class ConceptRoleWriter {

    private final ReferenceAdapter referenceAdapter;

    public ConceptRoleWriter(ReferenceAdapter referenceAdapter) {
        this.referenceAdapter = referenceAdapter;
    }

    public void write(JsonGenerator jsonGenerator, List<ArtefactReference> conceptRoles) throws IOException {
        jsonGenerator.writeFieldName(StructureUtils.CONCEPT_ROLES);
        jsonGenerator.writeStartArray();
        if (conceptRoles != null && !conceptRoles.isEmpty()) {
            for (ArtefactReference structureItemReference : conceptRoles) {
                jsonGenerator.writeString(referenceAdapter.toAdaptedUrn(structureItemReference));
            }
        }
        jsonGenerator.writeEndArray();
    }
}
