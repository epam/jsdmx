package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Concept;
import com.epam.jsdmx.infomodel.sdmx30.ConceptScheme;
import com.epam.jsdmx.infomodel.sdmx30.IsoConceptReference;

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

                    IsoConceptReference isoConceptReference = concept.getIsoConceptReference();
                    writeIsoConceptReference(jsonGenerator, isoConceptReference);
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

    private void writeIsoConceptReference(JsonGenerator jsonGenerator, IsoConceptReference isoConceptReference) throws IOException {
        if (isoConceptReference != null) {
            jsonGenerator.writeFieldName(StructureUtils.ISO_CONCEPT_REFERENCE);
            jsonGenerator.writeStartObject();

            String schemeId = isoConceptReference.getSchemeId();
            if (schemeId != null) {
                jsonGenerator.writeStringField(StructureUtils.CONCEPT_SCHEME_ID, schemeId);
            }

            String conceptId = isoConceptReference.getConceptId();
            if (conceptId != null) {
                jsonGenerator.writeStringField(StructureUtils.CONCEPT_ID, conceptId);
            }

            String agency = isoConceptReference.getAgency();
            if (agency != null) {
                jsonGenerator.writeStringField(StructureUtils.CONCEPT_AGENCY, agency);
            }

            jsonGenerator.writeEndObject();
        }
    }
}
