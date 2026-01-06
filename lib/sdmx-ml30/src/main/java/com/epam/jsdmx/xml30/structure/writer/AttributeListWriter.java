package com.epam.jsdmx.xml30.structure.writer;

import java.net.URI;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.AttributeDescriptor;
import com.epam.jsdmx.infomodel.sdmx30.AttributeRelationship;
import com.epam.jsdmx.infomodel.sdmx30.DataAttribute;
import com.epam.jsdmx.infomodel.sdmx30.DimensionRelationshipImpl;
import com.epam.jsdmx.infomodel.sdmx30.GroupRelationshipImpl;
import com.epam.jsdmx.infomodel.sdmx30.MeasureRelationship;
import com.epam.jsdmx.infomodel.sdmx30.MetadataAttributeRef;
import com.epam.jsdmx.infomodel.sdmx30.ObservationRelationshipImpl;
import com.epam.jsdmx.infomodel.sdmx30.Representation;

import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

@AllArgsConstructor
public class AttributeListWriter {

    private final AnnotableWriter annotableWriter;
    private final RepresentationWriter representationWriter;

    private static void writeMetaAttributeReference(XMLStreamWriter writer, MetadataAttributeRef metadataAttributeRef) throws XMLStreamException {
        writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.METADATA_ATTRIBUTE_REFERENCE);
        XmlWriterUtils.writeCharacters(metadataAttributeRef.getId(), writer);
        writer.writeEndElement();
    }

    public void writeAttributeList(AttributeDescriptor attributeDescriptor, XMLStreamWriter writer) throws XMLStreamException {
        if (attributeDescriptor != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.ATTRIBUTE_LIST);

            XmlWriterUtils.writeIdUriAttributes(writer, XmlConstants.ATTRIBUTE_DESCRIPTOR_ID, attributeDescriptor.getUri());
            if (attributeDescriptor.getContainer() != null) {
                XmlWriterUtils.writeUrn(attributeDescriptor.getUrn(), writer);
            }

            annotableWriter.write(attributeDescriptor, writer);
            List<DataAttribute> components = attributeDescriptor.getComponents();
            List<MetadataAttributeRef> metadataAttributes = attributeDescriptor.getMetadataAttributes();
            writeMetadataAttributeUsage(metadataAttributes, writer);
            writeAttribute(components, writer);
            writer.writeEndElement();
        }

    }

    private void writeMetadataAttributeUsage(List<MetadataAttributeRef> metadataAttributes, XMLStreamWriter writer) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(metadataAttributes)) {
            for (MetadataAttributeRef metadataAttributeRef : metadataAttributes) {
                writeMetaAttribute(writer, metadataAttributeRef);
            }
        }
    }

    private void writeMetaAttribute(XMLStreamWriter writer, MetadataAttributeRef metadataAttributeRef) throws XMLStreamException {
        if (metadataAttributeRef != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.METADATA_ATTRIBUTE_USAGE);
            URI uri = metadataAttributeRef.getUri();
            if (uri != null) {
                writer.writeAttribute(XmlConstants.URI, uri.toString());
            }
            if (metadataAttributeRef.getContainer() != null) {
                String urn = metadataAttributeRef.getUrn();
                if (urn != null) {
                    writer.writeAttribute(XmlConstants.URN, urn);
                }
            }
            annotableWriter.write(metadataAttributeRef, writer);
            writeMetaAttributeReference(writer, metadataAttributeRef);
            writeAttributeRelationship(writer, metadataAttributeRef.getMetadataRelationship());
            writer.writeEndElement();
        }
    }

    private void writeAttribute(List<DataAttribute> components, XMLStreamWriter writer) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(components)) {
            for (DataAttribute attribute : components) {
                writeAttribute(writer, attribute);
            }
        }
    }

    private void writeAttribute(XMLStreamWriter writer, DataAttribute attribute) throws XMLStreamException {
        if (attribute != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.ATTRIBUTE);
            XmlWriterUtils.writeIdUriAttributes(writer, attribute.getId(), attribute.getUri());
            if (attribute.getContainer() != null) {
                XmlWriterUtils.writeUrn(attribute.getUrn(), writer);
            }
            boolean mandatory = attribute.isMandatory();
            writer.writeAttribute(XmlConstants.USAGE, mandatory ? XmlConstants.MANDATORY : XmlConstants.OPTIONAL);
            this.annotableWriter.write(attribute, writer);
            XmlWriterUtils.writeConceptIdentity(writer, attribute);
            Representation localRepresentation = attribute.getLocalRepresentation();
            if (localRepresentation != null) {
                representationWriter.writeRepresentation(writer, localRepresentation, XmlConstants.LOCAL_REPRESENTATION);
            }
            XmlWriterUtils.writeConceptRoles(writer, attribute.getConceptRoles());
            writeAttributeRelationship(writer, attribute.getAttributeRelationship());
            writeMeasureRelationship(writer, attribute.getMeasureRelationship());
            writer.writeEndElement();
        }
    }

    private void writeMeasureRelationship(XMLStreamWriter writer, MeasureRelationship measureRelationship) throws XMLStreamException {
        if (measureRelationship != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.MEASURE_RELATIONSHIP);
            List<String> measures = measureRelationship.getMeasures();
            if (CollectionUtils.isNotEmpty(measures)) {
                for (String measure : measures) {
                    if (measure != null) {
                        writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.MEASURE);
                        writer.writeCharacters(measure);
                        writer.writeEndElement();
                    }
                }
            }
            writer.writeEndElement();
        }
    }

    private void writeAttributeRelationship(XMLStreamWriter writer, AttributeRelationship attributeRelationship) throws XMLStreamException {
        if (attributeRelationship != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.ATTRIBUTE_RELATIONSHIP);
            if (attributeRelationship instanceof DimensionRelationshipImpl) {
                DimensionRelationshipImpl dimensionRelationship = (DimensionRelationshipImpl) attributeRelationship;
                writeDimensions(writer, dimensionRelationship);
            }

            if (attributeRelationship instanceof ObservationRelationshipImpl) {
                writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.OBSERVATION);
                writer.writeEndElement();
            }

            if (attributeRelationship instanceof GroupRelationshipImpl) {
                GroupRelationshipImpl groupRelationship = (GroupRelationshipImpl) attributeRelationship;
                writeGroup(writer, groupRelationship);
            }
            writer.writeEndElement();
        }
    }

    private void writeGroup(XMLStreamWriter writer, GroupRelationshipImpl groupRelationship) throws XMLStreamException {
        writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.GROUP);
        if (groupRelationship.getGroupKey() != null) {
            writer.writeCharacters(groupRelationship.getGroupKey());
        }
        writer.writeEndElement();
    }

    private void writeDimensions(XMLStreamWriter writer, DimensionRelationshipImpl dimensionRelationship) throws XMLStreamException {
        List<String> dimensions = dimensionRelationship.getDimensions();
        if (CollectionUtils.isNotEmpty(dimensions)) {
            for (String dimension : dimensions) {
                if (dimension != null) {
                    writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.DIMENSION);
                    writer.writeCharacters(dimension);
                    writer.writeEndElement();
                }
            }
        }
    }
}
