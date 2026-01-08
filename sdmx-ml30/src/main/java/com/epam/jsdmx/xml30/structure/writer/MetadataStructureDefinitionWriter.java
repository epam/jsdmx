package com.epam.jsdmx.xml30.structure.writer;

import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.MetadataAttribute;
import com.epam.jsdmx.infomodel.sdmx30.MetadataAttributeDescriptor;
import com.epam.jsdmx.infomodel.sdmx30.MetadataStructureDefinition;

import org.apache.commons.collections4.CollectionUtils;

public class MetadataStructureDefinitionWriter extends XmlWriter<MetadataStructureDefinition> {

    private final RepresentationWriter representationWriter;

    public MetadataStructureDefinitionWriter(NameableWriter nameableWriter,
                                             AnnotableWriter annotableWriter,
                                             CommonAttributesWriter commonAttributesWriter,
                                             LinksWriter linksWriter,
                                             RepresentationWriter representationWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
        this.representationWriter = representationWriter;
    }

    @Override
    protected void writeAttributes(MetadataStructureDefinition metadataStructureDefinition, XMLStreamWriter writer) throws XMLStreamException {
        this.commonAttributesWriter.writeAttributes(metadataStructureDefinition, writer);
    }

    @Override
    protected void writeCustomAttributeElements(MetadataStructureDefinition metadataStructureDefinition, XMLStreamWriter writer) throws XMLStreamException {
        writeMetadataStructureComponents(metadataStructureDefinition.getAttributeDescriptor(), writer);
    }

    @Override
    protected String getName() {
        return XmlConstants.METADATA_STRUCTURE;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.METADATA_STRUCTURES;
    }

    @Override
    protected Set<MetadataStructureDefinition> extractArtefacts(Artefacts artefacts) {
        return artefacts.getMetadataStructureDefinitions();
    }

    private void writeMetadataStructureComponents(MetadataAttributeDescriptor attributeDescriptor, XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.METADATA_STRUCTURE_COMPONENTS);
        if (attributeDescriptor != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.METADATA_ATTRIBUTE_LIST);

            if (attributeDescriptor.getContainer() != null) {
                writer.writeAttribute(XmlConstants.URN, attributeDescriptor.getUrn());
            }

            this.annotableWriter.write(attributeDescriptor, writer);
            List<MetadataAttribute> components = attributeDescriptor.getComponents();
            writeMetadataAttributes(writer, components);

            writer.writeEndElement();
        }
        writer.writeEndElement();
    }

    private void writeMetadataAttributes(XMLStreamWriter writer, List<MetadataAttribute> components) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(components)) {
            for (MetadataAttribute attribute : components) {
                writeMetadataAttribute(attribute, writer);
            }
        }
    }

    private void writeMetadataAttribute(MetadataAttribute attribute, XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.METADATA_ATTRIBUTE);
        XmlWriterUtils.writeIdUriAttributes(writer, attribute.getId(), attribute.getUri());
        if (attribute.getContainer() != null) {
            writer.writeAttribute(XmlConstants.URN, attribute.getUrn());
        }

        writer.writeAttribute(XmlConstants.MIN_OCCURS, String.valueOf(attribute.getMinOccurs()));
        writer.writeAttribute(XmlConstants.MAX_OCCURS, String.valueOf(attribute.getMaxOccurs()));
        writer.writeAttribute(XmlConstants.IS_PRESENTATIONAL, String.valueOf(attribute.isPresentational()));

        this.annotableWriter.write(attribute, writer);
        XmlWriterUtils.writeConceptIdentity(writer, attribute);
        representationWriter.writeRepresentation(writer, attribute.getLocalRepresentation(), XmlConstants.LOCAL_REPRESENTATION);
        writeMetadataAttributes(writer, attribute.getHierarchy());
        writer.writeEndElement();
    }
}