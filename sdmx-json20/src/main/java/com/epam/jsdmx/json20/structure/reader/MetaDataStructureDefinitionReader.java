package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataAttribute;
import com.epam.jsdmx.infomodel.sdmx30.MetadataAttributeDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataAttributeImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataStructureDefinitionImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.apache.commons.collections.CollectionUtils;

public class MetaDataStructureDefinitionReader extends MaintainableReader<MetadataStructureDefinitionImpl> {

    private final IdentifiableReader identifiableReader;

    public MetaDataStructureDefinitionReader(VersionableReader versionableArtefact, IdentifiableReader identifiableReader) {
        super(versionableArtefact);
        this.identifiableReader = identifiableReader;
    }

    @Override
    protected MetadataStructureDefinitionImpl createMaintainableArtefact() {
        return new MetadataStructureDefinitionImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, MetadataStructureDefinitionImpl dataStructureDefinition) throws IOException {
        String fieldName = parser.getCurrentName();
        if (StructureUtils.METADATA_STRUCTURE_COMPONENTS.equals(fieldName)) {
            dataStructureDefinition.setAttributeDescriptor(getAttributeDescriptor(parser));
        } else {
            parser.skipChildren();
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.METADATA_STRUCTURES;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<MetadataStructureDefinitionImpl> artefacts) {
        artefact.getMetadataStructureDefinitions().addAll(artefacts);
    }

    private MetadataAttributeDescriptorImpl getAttributeDescriptor(JsonParser parser) throws IOException {
        parser.nextToken();
        var metadataAttributeDescriptor = new MetadataAttributeDescriptorImpl();
        if (parser.currentToken()
            .equals(JsonToken.START_OBJECT) && parser.nextToken()
            .equals(JsonToken.END_OBJECT)) {
            return null;
        }
        while (parser.currentToken() != JsonToken.END_OBJECT) {
            checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String fieldName = parser.getCurrentName();
            if (StructureUtils.METADATA_ATTRIBUTE_LIST.equals(fieldName)) {
                metadataAttributeDescriptor = getMetadataAttribute(parser);
                parser.nextToken();
            } else {
                throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "MetadataAttributeDescriptor: " + fieldName);
            }
        }
        return metadataAttributeDescriptor;
    }

    private MetadataAttributeDescriptorImpl getMetadataAttribute(JsonParser parser) throws IOException {
        parser.nextToken();
        MetadataAttributeDescriptorImpl metadataAttributeDescriptor = new MetadataAttributeDescriptorImpl();
        if (parser.currentToken()
            .equals(JsonToken.START_OBJECT) && parser.nextToken()
            .equals(JsonToken.END_OBJECT)) {
            return null;
        }
        while (parser.currentToken() != JsonToken.END_OBJECT) {
            checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String fieldName = parser.getCurrentName();
            if (StructureUtils.METADATA_ATTRIBUTES.equals(fieldName)) {
                List<MetadataAttribute> metadataComponents = ReaderUtils.getArray(parser, this::getComponent);
                if (CollectionUtils.isNotEmpty(metadataComponents)) {
                    metadataAttributeDescriptor.setComponents(metadataComponents);
                }
            } else {
                identifiableReader.read(metadataAttributeDescriptor, parser);
            }
            parser.nextToken();
        }
        return metadataAttributeDescriptor;
    }

    private MetadataAttributeImpl getComponent(JsonParser parser) {
        try {
            MetadataAttributeImpl metadataAttribute = new MetadataAttributeImpl();
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.CONCEPT_IDENTITY:
                        metadataAttribute.setConceptIdentity(getConceptIdentity(parser));
                        break;
                    case StructureUtils.MIN_OCCURS:
                        metadataAttribute.setMinOccurs(ReaderUtils.getIntJsonField(parser));
                        break;
                    case StructureUtils.MAX_OCCURS:
                        metadataAttribute.setMaxOccurs(ReaderUtils.getIntJsonField(parser));
                        break;
                    case StructureUtils.LOCAL_REPRESENTATION:
                        RepresentationReader representationReader = new RepresentationReader();
                        metadataAttribute.setLocalRepresentation(representationReader.getRepresentations(parser));
                        break;
                    case StructureUtils.IS_PRESENTATIONAL:
                        boolean isPresentational = ReaderUtils.getBooleanJsonField(parser);
                        metadataAttribute.setPresentational(isPresentational);
                        break;
                    case StructureUtils.METADATA_ATTRIBUTES:
                        List<MetadataAttribute> metadataComponents = ReaderUtils.getArray(parser, this::getComponent);
                        if (CollectionUtils.isNotEmpty(metadataComponents)) {
                            metadataAttribute.setHierarchy(metadataComponents);
                        }
                        break;
                    default:
                        identifiableReader.read(metadataAttribute, parser);
                        break;
                }
            }
            return metadataAttribute;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    private IdentifiableArtefactReferenceImpl getConceptIdentity(JsonParser parser) throws IOException {
        String conceptIdentity = ReaderUtils.getStringJsonField(parser);
        if (conceptIdentity != null) {
            return new IdentifiableArtefactReferenceImpl(conceptIdentity);
        }
        return null;
    }
}
