package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableObjectSelectionImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.MetadataflowImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import org.apache.commons.collections.CollectionUtils;

public class MetadataFlowReader extends MaintainableReader<MetadataflowImpl> {

    public MetadataFlowReader(VersionableReader versionableArtefact) {
        super(versionableArtefact);
    }

    @Override
    protected MetadataflowImpl createMaintainableArtefact() {
        return new MetadataflowImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, MetadataflowImpl dataflow) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.STRUCTURE:
                String urn = ReaderUtils.getStringJsonField(parser);
                if (urn != null) {
                    dataflow.setStructure(new MaintainableArtefactReference(urn));
                }
                break;
            case StructureUtils.TARGETS:
                List<String> artefactRefs = ReaderUtils.getListStrings(parser);
                if (CollectionUtils.isNotEmpty(artefactRefs)) {
                    List<ArtefactReference> maintainableArtefactReferences = stringsToArtefactReferences(artefactRefs);
                    IdentifiableObjectSelectionImpl identifiableObjectSelection = new IdentifiableObjectSelectionImpl();
                    identifiableObjectSelection.setResolvesTo(maintainableArtefactReferences);
                    dataflow.setSelections(List.of(identifiableObjectSelection));
                }
                break;
            default:
                throw new IllegalStateException(StructureUtils.NO_SUCH_PROPERTY_IN + "Metadataflow: " + fieldName);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.METADATAFLOWS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<MetadataflowImpl> artefacts) {
        artefact.getMetadataflows().addAll(artefacts);
    }

    private List<ArtefactReference> stringsToArtefactReferences(List<String> artefectRefs) {
        return artefectRefs.stream()
            .filter(Objects::nonNull)
            .map(MaintainableArtefactReference::new)
            .collect(Collectors.toList());
    }
}
