package com.epam.jsdmx.json10.structure.writer;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Concept;
import com.epam.jsdmx.infomodel.sdmx30.ConceptScheme;

import com.fasterxml.jackson.core.JsonGenerator;

public class ConceptSchemeWriter extends MaintainableWriter<ConceptScheme> {

    private final NameableWriter nameableWriter;
    private final RepresentationWriter representationWriter;

    public ConceptSchemeWriter(VersionableWriter versionableWriter,
                               LinksWriter linksWriter,
                               NameableWriter nameableWriter,
                               RepresentationWriter representationWriter) {
        super(versionableWriter, linksWriter);
        this.nameableWriter = nameableWriter;
        this.representationWriter = representationWriter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, ConceptScheme conceptScheme) throws IOException {
        super.writeFields(jsonGenerator, conceptScheme);
        List<? extends Concept> conceptSchemeItems = conceptScheme.getItems();
        if (conceptSchemeItems != null) {
            jsonGenerator.writeFieldName(StructureUtils.CONCEPTS);
            jsonGenerator.writeStartArray();
            if (!conceptSchemeItems.isEmpty()) {
                for (Concept concept : conceptSchemeItems) {
                    jsonGenerator.writeStartObject();
                    nameableWriter.write(jsonGenerator, concept);

                    representationWriter.writeRepresentation(jsonGenerator, concept.getCoreRepresentation(), StructureUtils.CORE_REPRESENTATION);

                    jsonGenerator.writeEndObject();
                }
            }
            jsonGenerator.writeEndArray();
        }
        jsonGenerator.writeBooleanField(StructureUtils.IS_PARTIAL, conceptScheme.isPartial());
    }

    @Override
    protected Set<ConceptScheme> extractArtefacts(Artefacts artefacts) {
        return artefacts.getConceptSchemes();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.CONCEPT_SCHEMES;
    }

}
