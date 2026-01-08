package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.AttributeDescriptor;
import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinition;
import com.epam.jsdmx.infomodel.sdmx30.DimensionDescriptor;
import com.epam.jsdmx.infomodel.sdmx30.MeasureDescriptor;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;
import com.epam.jsdmx.serializer.common.TimeDimensionLocalRepresentationAdapter;

import com.fasterxml.jackson.core.JsonGenerator;


public class DataStructureDefinitionWriter extends MaintainableWriter<DataStructureDefinition> {

    private final AttributeListWriter attributeWriter;
    private final DimensionListWriter dimensionWriter;
    private final GroupDimensionListWriter groupDimensionWriter;
    private final MeasureListWriter measureWriter;
    private final ReferenceAdapter referenceAdapter;
    private final TimeDimensionLocalRepresentationAdapter dsdAdapter;


    public DataStructureDefinitionWriter(VersionableWriter versionableWriter,
                                         LinksWriter linksWriter,
                                         AttributeListWriter attributeWriter,
                                         DimensionListWriter dimensionWriter,
                                         GroupDimensionListWriter groupDimensionListWriter,
                                         MeasureListWriter measureWriter,
                                         ReferenceAdapter referenceAdapter,
                                         TimeDimensionLocalRepresentationAdapter dsdAdapter) {
        super(versionableWriter, linksWriter);
        this.attributeWriter = attributeWriter;
        this.dimensionWriter = dimensionWriter;
        this.groupDimensionWriter = groupDimensionListWriter;
        this.measureWriter = measureWriter;
        this.referenceAdapter = referenceAdapter;
        this.dsdAdapter = dsdAdapter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, DataStructureDefinition dsd) throws IOException {
        super.writeFields(jsonGenerator, dsd);
        writeDataStructureComponents(jsonGenerator, dsd);
        writeMetadata(jsonGenerator, dsd.getMetadataStructure());
    }

    @Override
    protected Set<DataStructureDefinition> extractArtefacts(Artefacts artefacts) {
        return artefacts.getDataStructures();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.DATA_STRUCTURES;
    }

    private void writeDataStructureComponents(JsonGenerator jsonGenerator, DataStructureDefinition dataStructureDefinition) throws IOException {
        dataStructureDefinition = dsdAdapter.adapt(dataStructureDefinition);
        AttributeDescriptor attributeDescriptor = dataStructureDefinition.getAttributeDescriptor();
        DimensionDescriptor dimensionDescriptor = dataStructureDefinition.getDimensionDescriptor();
        MeasureDescriptor measureDescriptor = dataStructureDefinition.getMeasureDescriptor();
        if (!DataStructureComponentsUtils.areAllEmpty(
            attributeDescriptor,
            dimensionDescriptor,
            measureDescriptor
        )) {
            jsonGenerator.writeFieldName(StructureUtils.DATA_STRUCTURE_COMPONENTS);
            jsonGenerator.writeStartObject();
            attributeWriter.write(jsonGenerator, attributeDescriptor);
            dimensionWriter.write(jsonGenerator, dimensionDescriptor);
            groupDimensionWriter.write(jsonGenerator, dataStructureDefinition.getGroupDimensionDescriptors());
            measureWriter.write(jsonGenerator, measureDescriptor);
            jsonGenerator.writeEndObject();
        }
    }

    private void writeMetadata(JsonGenerator jsonGenerator, ArtefactReference metadata) throws IOException {
        if (metadata != null) {
            jsonGenerator.writeStringField(StructureUtils.METADATA, referenceAdapter.toAdaptedUrn(metadata));
        }
    }

}
