package com.epam.jsdmx.xml30.structure.writer;

import static com.epam.jsdmx.xml30.structure.writer.XmlConstants.STRUCTURE_UPPER;
import static com.epam.jsdmx.xml30.structure.writer.XmlWriterUtils.writeCharacters;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableObjectSelection;
import com.epam.jsdmx.infomodel.sdmx30.Metadataflow;

import org.apache.commons.collections4.CollectionUtils;

public class MetadataflowWriter extends XmlWriter<Metadataflow> {

    public MetadataflowWriter(NameableWriter nameableWriter,
                              AnnotableWriter annotableWriter,
                              CommonAttributesWriter commonAttributesWriter,
                              LinksWriter linksWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
    }

    @Override
    protected void writeAttributes(Metadataflow metadataflow, XMLStreamWriter writer) throws XMLStreamException {
        this.commonAttributesWriter.writeAttributes(metadataflow, writer);
    }

    @Override
    protected void writeCustomAttributeElements(Metadataflow metadataflow, XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement(XmlConstants.STRUCTURE + STRUCTURE_UPPER);
        ArtefactReference structure = metadataflow.getStructure();
        if (structure != null) {
            writeCharacters(structure.getUrn(), writer);
        }
        writer.writeEndElement();

        List<IdentifiableObjectSelection> selections = metadataflow.getSelections();
        if (CollectionUtils.isNotEmpty(selections)) {
            List<ArtefactReference> artefactReferences = selections.stream()
                .filter(Objects::nonNull)
                .flatMap(section -> section.getResolvesTo().stream())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

            for (ArtefactReference reference : artefactReferences) {
                writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.TARGET);
                writeCharacters(reference.getUrn(), writer);
                writer.writeEndElement();
            }
        }
    }

    @Override
    protected String getName() {
        return XmlConstants.METADATA_FLOW;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.METADATA_FLOWS;
    }

    @Override
    protected Set<Metadataflow> extractArtefacts(Artefacts artefacts) {
        return artefacts.getMetadataflows();
    }
}