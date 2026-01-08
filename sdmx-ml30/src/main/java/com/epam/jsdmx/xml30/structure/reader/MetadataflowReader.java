package com.epam.jsdmx.xml30.structure.reader;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableObjectSelectionImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.MetadataflowImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

public class MetadataflowReader extends XmlReader<MetadataflowImpl> {

    private final List<ArtefactReference> artefactReferences = new ArrayList<>();

    public MetadataflowReader(AnnotableReader annotableReader,
                              NameableReader nameableReader) {
        super(annotableReader, nameableReader);
    }

    @Override
    protected MetadataflowImpl createMaintainableArtefact() {
        return new MetadataflowImpl();
    }

    @Override
    protected MetadataflowImpl read(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        MetadataflowImpl metadataflow = super.read(reader);
        var objectSelection = new IdentifiableObjectSelectionImpl();
        objectSelection.setResolvesTo(new ArrayList<>(artefactReferences));
        metadataflow.setSelections(List.of(objectSelection));
        return metadataflow;
    }

    @Override
    protected void read(XMLStreamReader reader, MetadataflowImpl metadataflow) throws URISyntaxException, XMLStreamException {
        String localName = reader.getLocalName();
        switch (localName) {
            case XmlConstants.STRUCTURE_UPPER:
                Optional.ofNullable(reader.getElementText())
                    .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                    .map(MaintainableArtefactReference::new)
                    .ifPresent(metadataflow::setStructure);
                break;
            case XmlConstants.TARGET:
                Optional.ofNullable(reader.getElementText())
                    .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                    .map(MaintainableArtefactReference::new)
                    .ifPresent(artefactReferences::add);
                break;
            default:
                throw new IllegalArgumentException("Metadataflow " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
        }
    }

    @Override
    protected String getName() {
        return XmlConstants.METADATA_FLOW;
    }

    @Override
    protected String getNames() {
        return XmlConstants.METADATA_FLOWS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<MetadataflowImpl> artefacts) {
        artefact.getMetadataflows().addAll(artefacts);
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, MetadataflowImpl metadataflow) {
        XmlReaderUtils.setCommonAttributes(reader, metadataflow);
    }

    @Override
    protected void clean() {
        artefactReferences.clear();
    }
}
