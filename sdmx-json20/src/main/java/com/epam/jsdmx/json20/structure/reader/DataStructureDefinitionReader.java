package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.AttributeDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinitionImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class DataStructureDefinitionReader extends MaintainableReader<DataStructureDefinitionImpl> {

    private final IdentifiableReader identifiableReader;

    public DataStructureDefinitionReader(VersionableReader versionableArtefact, IdentifiableReader identifiableReader) {
        super(versionableArtefact);
        this.identifiableReader = identifiableReader;
    }

    @Override
    protected DataStructureDefinitionImpl createMaintainableArtefact() {
        return new DataStructureDefinitionImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, DataStructureDefinitionImpl dataStructureDefinition) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.METADATA:
                String metadataUrn = ReaderUtils.getStringJsonField(parser);
                if (metadataUrn != null) {
                    dataStructureDefinition.setMetadataStructure(new MaintainableArtefactReference(metadataUrn));
                }
                break;
            case StructureUtils.DATA_STRUCTURE_COMPONENTS:
                getDataStructureComponents(parser, dataStructureDefinition);
                break;
            default:
                throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "DataStructureDefinition: " + fieldName);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.DATA_STRUCTURES;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<DataStructureDefinitionImpl> artefacts) {
        artefact.getDataStructures().addAll(artefacts);
    }

    private void getDataStructureComponents(JsonParser parser, DataStructureDefinitionImpl dataStructureDefinition) throws IOException {
        parser.nextToken();
        if (ReaderUtils.isNullValue(parser)) {
            return;
        }
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String fieldName = parser.getCurrentName();
            switch (fieldName) {
                case StructureUtils.ATTRIBUTE_LIST:
                    AttributeListReader attributeListReader = new AttributeListReader();
                    AttributeDescriptorImpl attributeDescriptor = attributeListReader.getAttributeDescriptor(parser);
                    dataStructureDefinition.setAttributeDescriptor(attributeDescriptor);
                    break;
                case StructureUtils.DIMENSION_LIST:
                    DimensionListReader dimensionListReader = new DimensionListReader(identifiableReader);
                    dataStructureDefinition.setDimensionDescriptor(dimensionListReader.getDimensionDescriptor(parser));
                    break;
                case StructureUtils.MEASURE_LIST:
                    MeasureListReader measureListReader = new MeasureListReader();
                    dataStructureDefinition.setMeasureDescriptor(measureListReader.getMeasureDescriptor(parser));
                    break;
                case StructureUtils.GROUPS:
                    parser.nextToken();
                    parser.skipChildren();
                    break;
                default:
                    throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "DataStructureComponent: " + fieldName);
            }
        }
    }

}
