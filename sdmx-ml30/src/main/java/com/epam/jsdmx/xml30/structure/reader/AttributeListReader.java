package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEndingTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.moveToNextTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.readConceptIdentity;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.readConceptRole;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.AttributeDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.AttributeRelationship;
import com.epam.jsdmx.infomodel.sdmx30.DataAttribute;
import com.epam.jsdmx.infomodel.sdmx30.DataAttributeImpl;
import com.epam.jsdmx.infomodel.sdmx30.DimensionRelationshipImpl;
import com.epam.jsdmx.infomodel.sdmx30.GroupRelationshipImpl;
import com.epam.jsdmx.infomodel.sdmx30.MeasureRelationshipImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataAttributeRef;
import com.epam.jsdmx.infomodel.sdmx30.MetadataAttributeRefImpl;
import com.epam.jsdmx.infomodel.sdmx30.ObservationRelationshipImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

@AllArgsConstructor
public class AttributeListReader {

    private final RepresentationReader representationReader;
    private final AnnotableReader annotableReader;

    public void read(XMLStreamReader reader, AttributeDescriptorImpl attributeDescriptor) throws XMLStreamException, URISyntaxException {
        attributeDescriptor.setId(XmlConstants.ATTRIBUTE_DESCRIPTOR_ID);
        String uri = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.URI);
        if (uri != null) {
            attributeDescriptor.setUri(new URI(uri));
        }
        List<DataAttribute> components = new ArrayList<>();
        List<MetadataAttributeRef> metadataAttributeRefs = new ArrayList<>();

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.ATTRIBUTE_LIST)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, attributeDescriptor);
                    break;
                case XmlConstants.ATTRIBUTE:
                    setAttribute(reader, components);
                    break;
                case XmlConstants.METADATA_ATTRIBUTE_USAGE:
                    metadataAttributeRefs.add(getMetaAttributeRefs(reader));
                    break;
                default:
                    throw new IllegalArgumentException("AttributeList " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
        attributeDescriptor.setMetadataAttributes(metadataAttributeRefs);
        attributeDescriptor.setComponents(components);
    }

    private MetadataAttributeRef getMetaAttributeRefs(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        var metadataAttributeRef = new MetadataAttributeRefImpl();
        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.METADATA_ATTRIBUTE_USAGE)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, metadataAttributeRef);
                    break;
                case XmlConstants.METADATA_ATTRIBUTE_REFERENCE:
                    Optional.ofNullable(reader.getElementText())
                        .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                        .ifPresent(metadataAttributeRef::setId);
                    break;
                case XmlConstants.ATTRIBUTE_RELATIONSHIP:
                    metadataAttributeRef.setMetadataRelationship(getAttributeRelationship(reader));
                    break;
                default:
                    throw new IllegalArgumentException("MetadataAttributeUsage " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
        return metadataAttributeRef;
    }

    private void setAttribute(XMLStreamReader reader, List<DataAttribute> components) throws XMLStreamException, URISyntaxException {
        var attribute = new DataAttributeImpl();
        List<ArtefactReference> conceptRoles = new ArrayList<>();
        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.ID))
            .ifPresent(attribute::setId);

        String uri = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.URI);
        if (uri != null) {
            attribute.setUri(new URI(uri));
        }

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.ATTRIBUTE)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, attribute);
                    break;
                case XmlConstants.CONCEPT_IDENTITY:
                    readConceptIdentity(reader, attribute);
                    break;
                case XmlConstants.CONCEPT_ROLE:
                    readConceptRole(reader, conceptRoles);
                    break;
                case XmlConstants.LOCAL_REPRESENTATION:
                    attribute.setLocalRepresentation(representationReader.readRepresentation(reader));
                    break;
                case XmlConstants.ATTRIBUTE_RELATIONSHIP:
                    attribute.setAttributeRelationship(getAttributeRelationship(reader));
                    break;
                case XmlConstants.MEASURE_RELATIONSHIP:
                    setMeasureRelationship(reader, attribute);
                    break;
                default:
                    throw new IllegalArgumentException("Attribute " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
        attribute.setConceptRoles(conceptRoles);
        attribute.setMaxOccurs(1); // todo: write to correct fields
        components.add(attribute);
    }

    private void setMeasureRelationship(XMLStreamReader reader, DataAttributeImpl attribute) throws XMLStreamException {
        List<String> measures = new ArrayList<>();
        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.MEASURE_RELATIONSHIP)) {
            String localName = reader.getLocalName();
            if (!XmlConstants.MEASURE.equals(localName)) {
                throw new IllegalArgumentException("MeasureRelationship " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }

            Optional.ofNullable(reader.getElementText())
                .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                .ifPresent(measures::add);

            moveToNextTag(reader);
        }
        var measureRelationship = new MeasureRelationshipImpl();
        measureRelationship.setMeasures(CollectionUtils.isNotEmpty(measures) ? measures : null);
        attribute.setMeasureRelationship(measureRelationship);
    }

    private AttributeRelationship getAttributeRelationship(XMLStreamReader reader) throws XMLStreamException {
        List<String> dimensions = new ArrayList<>();
        var dimensionRelationship = new DimensionRelationshipImpl();
        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.ATTRIBUTE_RELATIONSHIP)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.DATAFLOW:
                    moveToNextTag(reader);
                    moveToNextTag(reader);
                    return null;
                case XmlConstants.DIMENSION:
                    Optional.ofNullable(reader.getElementText())
                        .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                        .ifPresent(dimensions::add);
                    break;
                case XmlConstants.OBSERVATION:
                    moveToNextTag(reader);
                    moveToNextTag(reader);
                    return new ObservationRelationshipImpl();
                case XmlConstants.GROUP:
                    var group = new GroupRelationshipImpl();
                    Optional.ofNullable(reader.getElementText())
                        .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                        .ifPresent(group::setGroupKey);

                    moveToNextTag(reader);
                    return group;
                default:
                    throw new IllegalArgumentException("AttributeRelationship " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }

        dimensionRelationship.setDimensions(dimensions);
        return dimensionRelationship;
    }
}
