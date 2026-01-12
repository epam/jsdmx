package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName;
import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.getStringJsonField;
import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.isNullValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.AttributeDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.AttributeRelationship;
import com.epam.jsdmx.infomodel.sdmx30.DataAttribute;
import com.epam.jsdmx.infomodel.sdmx30.DataAttributeImpl;
import com.epam.jsdmx.infomodel.sdmx30.DimensionRelationshipImpl;
import com.epam.jsdmx.infomodel.sdmx30.GroupRelationshipImpl;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.MeasureRelationship;
import com.epam.jsdmx.infomodel.sdmx30.MeasureRelationshipImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataAttributeRef;
import com.epam.jsdmx.infomodel.sdmx30.MetadataAttributeRefImpl;
import com.epam.jsdmx.infomodel.sdmx30.ObservationRelationshipImpl;
import com.epam.jsdmx.infomodel.sdmx30.Representation;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.apache.commons.collections.CollectionUtils;


public class AttributeListReader {

    private final IdentifiableReader identifiableReader = new IdentifiableReader(
        new AnnotableReader());

    public AttributeDescriptorImpl getAttributeDescriptor(JsonParser parser) throws IOException {
        parser.nextToken();
        if (isNullValue(parser)) {
            return null;
        }
        AttributeDescriptorImpl attributeDescriptor = new AttributeDescriptorImpl();
        while (!JsonToken.END_OBJECT.equals(parser.nextToken())) {
            checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String fieldName = parser.getCurrentName();
            switch (fieldName) {
                case StructureUtils.METADATA_ATTRIBUTE_USAGES:
                    List<MetadataAttributeRefImpl> metadataAttributeRefs = ReaderUtils.getArray(parser, this::getMetadataAttributeRef);
                    if (CollectionUtils.isNotEmpty(metadataAttributeRefs)) {
                        List<MetadataAttributeRef> attributeRefs = metadataAttributeRefs.stream()
                            .map(MetadataAttributeRef.class::cast)
                            .collect(Collectors.toList());
                        attributeDescriptor.setMetadataAttributes(attributeRefs);
                    }
                    break;
                case StructureUtils.ATTRIBUTES:
                    List<DataAttribute> components = getComponents(parser);
                    if (CollectionUtils.isNotEmpty(components)) {
                        attributeDescriptor.setComponents(components);
                    }
                    break;
                default:
                    identifiableReader.read(attributeDescriptor, parser);
                    break;
            }
        }
        attributeDescriptor.setId(StructureUtils.ATTRIBUTE_DESCRIPTOR_ID);
        return attributeDescriptor;
    }

    private List<DataAttribute> getComponents(JsonParser parser) throws IOException {
        List<DataAttribute> dataAttributes = new ArrayList<>();
        while (!JsonToken.END_ARRAY.equals(parser.nextToken())) {
            if (JsonToken.START_OBJECT.equals(parser.currentToken())) {
                DataAttributeImpl dataAttribute = new DataAttributeImpl();
                getComponent(parser, dataAttribute);
                dataAttributes.add(dataAttribute);
            }
        }
        return dataAttributes;
    }

    private void getComponent(JsonParser parser, DataAttributeImpl dataAttribute) throws IOException {
        dataAttribute.setMaxOccurs(1);
        while (!JsonToken.END_OBJECT.equals(parser.nextToken())) {
            checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String fieldName = parser.getCurrentName();
            switch (fieldName) {
                case StructureUtils.CONCEPT_IDENTITY:
                    String conceptIdentity = getStringJsonField(parser);
                    if (conceptIdentity != null) {
                        dataAttribute.setConceptIdentity(new IdentifiableArtefactReferenceImpl(conceptIdentity));
                    }
                    break;
                case StructureUtils.IS_MANDATORY:
                    boolean isMandatory = ReaderUtils.getBooleanJsonField(parser);
                    if (isMandatory) {
                        dataAttribute.setMinOccurs(1);
                    } else {
                        dataAttribute.setMinOccurs(0);
                    }
                    break;
                case StructureUtils.CONCEPT_ROLES:
                    List<ArtefactReference> conceptRoles = ConceptRoleUtils.getConceptRoles(parser);
                    if (CollectionUtils.isNotEmpty(conceptRoles)) {
                        dataAttribute.setConceptRoles(conceptRoles);
                    }
                    break;
                case StructureUtils.ATTRIBUTE_RELATIONSHIP:
                    dataAttribute.setAttributeRelationship(getAttributeRelationShip(parser));
                    parser.nextToken();
                    break;
                case StructureUtils.MEASURE_RELATIONSHIP:
                    dataAttribute.setMeasureRelationship(getMeasureRelationship(parser));
                    break;
                case StructureUtils.LOCAL_REPRESENTATION:
                    dataAttribute.setLocalRepresentation(getRepresentation(parser));
                    break;
                default:
                    identifiableReader.read(dataAttribute, parser);
                    break;
            }
        }
    }

    private MeasureRelationship getMeasureRelationship(JsonParser parser) throws IOException {
        MeasureRelationshipImpl measureRelationship = new MeasureRelationshipImpl();
        measureRelationship.setMeasures(ReaderUtils.getListStrings(parser));
        return measureRelationship;
    }

    private Representation getRepresentation(JsonParser parser) throws IOException {
        RepresentationReader representationReader = new RepresentationReader();
        return representationReader.getRepresentations(parser);
    }

    private MetadataAttributeRefImpl getMetadataAttributeRef(JsonParser parser) {
        if (JsonToken.VALUE_NULL.equals(parser.currentToken())) {
            return null;
        }
        try {
            MetadataAttributeRefImpl metadataAttributeRef = new MetadataAttributeRefImpl();
            while (!JsonToken.END_OBJECT.equals(parser.nextToken())) {
                checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.METADATA_ATTRIBUTE_REFERENCE:
                        String attributeId = getStringJsonField(parser);
                        if (attributeId != null) {
                            metadataAttributeRef.setId(attributeId);
                        }
                        break;
                    case StructureUtils.ATTRIBUTE_RELATIONSHIP:
                        metadataAttributeRef.setMetadataRelationship(getAttributeRelationShip(parser));
                        parser.nextToken();
                        break;
                    default:
                        identifiableReader.read(metadataAttributeRef, parser);
                }
            }
            return metadataAttributeRef;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    private AttributeRelationship getAttributeRelationShip(JsonParser parser) throws IOException {
        parser.nextToken();
        if (isNullValue(parser)) {
            return null;
        }
        while (!JsonToken.END_OBJECT.equals(parser.nextToken())) {
            checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String fieldName = parser.currentName();
            switch (fieldName) {
                case StructureUtils.DIMENSIONS:
                    DimensionRelationshipImpl dimensionRelationship = new DimensionRelationshipImpl();
                    dimensionRelationship.setDimensions(ReaderUtils.getListStrings(parser));
                    return dimensionRelationship;
                case StructureUtils.OBSERVATION:
                    parser.nextToken();
                    parser.nextToken();
                    return new ObservationRelationshipImpl();
                case StructureUtils.GROUP:
                    GroupRelationshipImpl groupRelationship = new GroupRelationshipImpl();
                    groupRelationship.setGroupKey(getStringJsonField(parser));
                    return groupRelationship;
                case StructureUtils.NONE:
                    parser.nextToken();
                    parser.nextToken();
                    return null;
                default:
                    throw new IllegalArgumentException("no such AttributeRelationship: " + fieldName);
            }
        }
        return null;
    }

}
