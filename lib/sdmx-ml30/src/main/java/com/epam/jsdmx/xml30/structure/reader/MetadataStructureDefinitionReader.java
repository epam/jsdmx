package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEndingTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.moveToNextTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.readConceptIdentity;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setCommonAttributes;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.MetadataAttribute;
import com.epam.jsdmx.infomodel.sdmx30.MetadataAttributeDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataAttributeImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataStructureDefinitionImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class MetadataStructureDefinitionReader extends XmlReader<MetadataStructureDefinitionImpl> {

    private final RepresentationReader representationReader;

    public MetadataStructureDefinitionReader(AnnotableReader annotableReader,
                                             NameableReader nameableReader,
                                             RepresentationReader representationReader) {
        super(annotableReader, nameableReader);
        this.representationReader = representationReader;
    }

    @Override
    protected MetadataStructureDefinitionImpl createMaintainableArtefact() {
        return new MetadataStructureDefinitionImpl();
    }

    @Override
    protected void read(XMLStreamReader reader,
                        MetadataStructureDefinitionImpl metadataStructureDefinition) throws URISyntaxException, XMLStreamException {
        String localName = reader.getLocalName();
        if (!XmlConstants.METADATA_STRUCTURE_COMPONENTS.equals(localName)) {
            throw new IllegalArgumentException("MetadataStructureDefinition " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
        }

        var metadataAttributeDescriptor = new MetadataAttributeDescriptorImpl();
        setMetadataStructureComponents(reader, metadataAttributeDescriptor);
        if (CollectionUtils.isNotEmpty(metadataAttributeDescriptor.getComponents())) {
            metadataStructureDefinition.setAttributeDescriptor(metadataAttributeDescriptor);
        }
    }

    private void setMetadataStructureComponents(XMLStreamReader reader,
                                                MetadataAttributeDescriptorImpl attributeDescriptor) throws XMLStreamException, URISyntaxException {
        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.METADATA_STRUCTURE_COMPONENTS)) {
            String name = reader.getLocalName();

            if (!XmlConstants.METADATA_ATTRIBUTE_LIST.equals(name)) {
                throw new IllegalArgumentException("MetadataStructureComponents " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + name);
            }
            setMetadataAttributeList(reader, attributeDescriptor);
            moveToNextTag(reader);
        }
    }

    private void setMetadataAttributeList(XMLStreamReader reader,
                                          MetadataAttributeDescriptorImpl attributeDescriptor) throws URISyntaxException, XMLStreamException {
        Optional.ofNullable(XmlReaderUtils.getId(reader))
            .ifPresent(attributeDescriptor::setId);

        String uri = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.URI);
        if (uri != null) {
            attributeDescriptor.setUri(new URI(uri));
        }

        List<MetadataAttribute> metadataAttributeList = new ArrayList<>();
        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.METADATA_ATTRIBUTE_LIST)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, attributeDescriptor);
                    break;
                case XmlConstants.METADATA_ATTRIBUTE:
                    MetadataAttributeImpl metadataAttribute = getMetadataAttribute(reader);
                    metadataAttributeList.add(metadataAttribute);
                    break;
                default:
                    throw new IllegalArgumentException("MetadataAttributeList " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
        attributeDescriptor.setComponents(metadataAttributeList);
    }

    private MetadataAttributeImpl getMetadataAttribute(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        var metadataAttribute = new MetadataAttributeImpl();
        List<MetadataAttribute> hierarchy = new ArrayList<>();

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.ID))
            .ifPresent(metadataAttribute::setId);

        String uri = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.URI);
        if (uri != null) {
            metadataAttribute.setUri(new URI(uri));
        }

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.MAX_OCCURS))
            .map(Integer::parseInt)
            .ifPresent(metadataAttribute::setMaxOccurs);

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.MIN_OCCURS))
            .map(Integer::parseInt)
            .ifPresent(metadataAttribute::setMinOccurs);

        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.IS_PRESENTATIONAL))
            .map(Boolean::parseBoolean)
            .ifPresent(metadataAttribute::setPresentational);

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.METADATA_ATTRIBUTE)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, metadataAttribute);
                    break;
                case XmlConstants.CONCEPT_IDENTITY:
                    readConceptIdentity(reader, metadataAttribute);
                    break;
                case XmlConstants.LOCAL_REPRESENTATION:
                    metadataAttribute.setLocalRepresentation(representationReader.readRepresentation(reader));
                    break;
                case XmlConstants.METADATA_ATTRIBUTE:
                    hierarchy.add(getMetadataAttribute(reader));
                    break;
                default:
                    throw new IllegalArgumentException("MetadataAttribute " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
        metadataAttribute.setHierarchy(hierarchy);
        return metadataAttribute;
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, MetadataStructureDefinitionImpl maintainableArtefact) throws XMLStreamException {
        setCommonAttributes(reader, maintainableArtefact);
    }

    @Override
    protected String getName() {
        return XmlConstants.METADATA_STRUCTURE;
    }

    @Override
    protected String getNames() {
        return XmlConstants.METADATA_STRUCTURES;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<MetadataStructureDefinitionImpl> artefacts) {
        artefact.getMetadataStructureDefinitions().addAll(artefacts);
    }
}