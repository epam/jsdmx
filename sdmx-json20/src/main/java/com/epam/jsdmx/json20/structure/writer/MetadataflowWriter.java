package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableObjectSelection;
import com.epam.jsdmx.infomodel.sdmx30.Metadataflow;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;

import com.fasterxml.jackson.core.JsonGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

@Slf4j
public class MetadataflowWriter extends MaintainableWriter<Metadataflow> {

    private final ReferenceAdapter referenceAdapter;

    public MetadataflowWriter(VersionableWriter versionableWriter,
                              LinksWriter linksWriter,
                              ReferenceAdapter referenceAdapter) {
        super(versionableWriter, linksWriter);
        this.referenceAdapter = referenceAdapter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, Metadataflow metadataflow) throws IOException {
        super.writeFields(jsonGenerator, metadataflow);
        if (metadataflow.getStructure() != null) {
            jsonGenerator.writeStringField(StructureUtils.STRUCTURE, referenceAdapter.toAdaptedUrn(metadataflow.getStructure()));
        }

        List<IdentifiableObjectSelection> selections = metadataflow.getSelections();
        writeSelections(jsonGenerator, selections);
    }

    @Override
    protected Set<Metadataflow> extractArtefacts(Artefacts artefacts) {
        return artefacts.getMetadataflows();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.METADATAFLOWS;
    }

    private void writeSelections(JsonGenerator jsonGenerator, List<IdentifiableObjectSelection> selections) throws IOException {
        if (CollectionUtils.isNotEmpty(selections)) {
            List<ArtefactReference> artefactReferences = selections.stream()
                .filter(Objects::nonNull)
                .flatMap(selection -> selection.getResolvesTo().stream())
                .collect(Collectors.toList());
            writeArtefactReferences(jsonGenerator, artefactReferences);
        }
    }

    private void writeArtefactReferences(JsonGenerator jsonGenerator, List<ArtefactReference> artefactReferences) throws IOException {
        if (CollectionUtils.isNotEmpty(artefactReferences)) {
            jsonGenerator.writeFieldName(StructureUtils.TARGETS);
            jsonGenerator.writeStartArray();
            for (ArtefactReference artefactReference : artefactReferences) {
                if (artefactReference != null) {
                    jsonGenerator.writeString(referenceAdapter.toAdaptedUrn(artefactReference));
                }
            }
            jsonGenerator.writeEndArray();
        }
    }
}
