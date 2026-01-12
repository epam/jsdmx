package com.epam.jsdmx.xml21.structure.writer;

import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.AttributeDescriptor;
import com.epam.jsdmx.infomodel.sdmx30.AttributeRelationship;
import com.epam.jsdmx.infomodel.sdmx30.DataAttribute;
import com.epam.jsdmx.infomodel.sdmx30.DimensionRelationshipImpl;
import com.epam.jsdmx.infomodel.sdmx30.GroupRelationshipImpl;
import com.epam.jsdmx.infomodel.sdmx30.ObservationRelationshipImpl;
import com.epam.jsdmx.infomodel.sdmx30.Representation;

import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

@AllArgsConstructor
public class AttributeListWriter {

    private final AnnotableWriter annotableWriter;
    private final RepresentationWriter representationWriter;
    private final ReferenceWriter referenceWriter;

    public void writeAttributeList(AttributeDescriptor attributeDescriptor, XMLStreamWriter writer) throws XMLStreamException {
        if (attributeDescriptor != null) {
            writer.writeStartElement(XmlConstants.STR + XmlConstants.ATTRIBUTE_LIST);

            XmlWriterUtils.writeIdUriAttributes(writer, XmlConstants.ATTRIBUTE_DESCRIPTOR_ID, attributeDescriptor.getUri());
            if (attributeDescriptor.getContainer() != null) {
                XmlWriterUtils.writeUrn(attributeDescriptor.getUrn(), writer);
            }

            annotableWriter.write(attributeDescriptor, writer);
            List<DataAttribute> components = attributeDescriptor.getComponents();
            writeAttribute(components, writer);
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
            writer.writeStartElement(XmlConstants.STR + XmlConstants.ATTRIBUTE);
            XmlWriterUtils.writeIdUriAttributes(writer, attribute.getId(), attribute.getUri());
            if (attribute.getContainer() != null) {
                XmlWriterUtils.writeUrn(attribute.getUrn(), writer);
            }
            boolean mandatory = attribute.isMandatory();
            writer.writeAttribute(XmlConstants.ASSIGNMENT_STATUS, mandatory ? XmlConstants.MANDATORY : XmlConstants.CONDITIONAL);
            this.annotableWriter.write(attribute, writer);
            referenceWriter.writeConceptIdentity(writer, attribute);
            Representation localRepresentation = attribute.getLocalRepresentation();
            if (localRepresentation != null) {
                representationWriter.writeRepresentation(writer, localRepresentation, XmlConstants.LOCAL_REPRESENTATION);
            }
            referenceWriter.writeConceptRoles(writer, attribute.getConceptRoles());
            writeAttributeRelationship(writer, attribute.getAttributeRelationship());
            writer.writeEndElement();
        }
    }

    private void writeAttributeRelationship(XMLStreamWriter writer, AttributeRelationship attributeRelationship) throws XMLStreamException {
        writer.writeStartElement(XmlConstants.STR + XmlConstants.ATTRIBUTE_RELATIONSHIP);
        if (attributeRelationship == null) {
            writer.writeEmptyElement(XmlConstants.STR + XmlConstants.NONE_RELATIONSHIP);
        } else if (attributeRelationship instanceof DimensionRelationshipImpl) {
            DimensionRelationshipImpl dimensionRelationship = (DimensionRelationshipImpl) attributeRelationship;
            writeDimensions(writer, dimensionRelationship);
            writeAttachmentGroups(writer, dimensionRelationship);
        } else if (attributeRelationship instanceof ObservationRelationshipImpl) {
            writer.writeStartElement(XmlConstants.STR + XmlConstants.PRIMARY_MEASURE);
            writeLocalRef(writer, XmlConstants.OBS_VALUE);
            writer.writeEndElement();
        } else if (attributeRelationship instanceof GroupRelationshipImpl) {
            GroupRelationshipImpl groupRelationship = (GroupRelationshipImpl) attributeRelationship;
            writeGroup(writer, groupRelationship);
        }
        writer.writeEndElement();
    }

    private void writeLocalRef(XMLStreamWriter writer, String value) throws XMLStreamException {
        referenceWriter.writeLocalReference(writer, value);
    }

    private void writeGroup(XMLStreamWriter writer, GroupRelationshipImpl groupRelationship) throws XMLStreamException {
        writer.writeStartElement(XmlConstants.STR + XmlConstants.GROUP);
        if (groupRelationship.getGroupKey() != null) {
            referenceWriter.writeLocalReference(writer, groupRelationship.getGroupKey());
        }
        writer.writeEndElement();
    }

    private void writeDimensions(XMLStreamWriter writer, DimensionRelationshipImpl dimensionRelationship) throws XMLStreamException {
        List<String> dimensions = dimensionRelationship.getDimensions();
        if (CollectionUtils.isNotEmpty(dimensions)) {
            for (String dimension : dimensions) {
                if (dimension != null) {
                    writer.writeStartElement(XmlConstants.STR + XmlConstants.DIMENSION);
                    writeLocalRef(writer, dimension);
                    writer.writeEndElement();
                }
            }
        }
    }

    private void writeAttachmentGroups(XMLStreamWriter writer, DimensionRelationshipImpl relationship) throws XMLStreamException {
        List<String> groupKeys = relationship.getGroupKeys();
        if (CollectionUtils.isNotEmpty(groupKeys)) {
            for (String key : groupKeys) {
                if (key != null) {
                    writer.writeStartElement(XmlConstants.STR + XmlConstants.ATTACHMENT_GROUP);
                    writeLocalRef(writer, key);
                    writer.writeEndElement();
                }
            }
        }
    }
}
