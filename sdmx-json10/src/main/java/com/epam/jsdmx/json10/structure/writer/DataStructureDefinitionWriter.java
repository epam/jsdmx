package com.epam.jsdmx.json10.structure.writer;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.AttributeDescriptor;
import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinition;
import com.epam.jsdmx.infomodel.sdmx30.DimensionDescriptor;
import com.epam.jsdmx.infomodel.sdmx30.GroupDimensionDescriptor;
import com.epam.jsdmx.infomodel.sdmx30.MeasureDescriptor;
import com.epam.jsdmx.serializer.common.TimeDimensionLocalRepresentationAdapter;
import com.epam.jsdmx.serializer.sdmx21.DataStructure30To21ComponentAdapter;

import com.fasterxml.jackson.core.JsonGenerator;


public class DataStructureDefinitionWriter extends MaintainableWriter<DataStructureDefinition> {

    private final AttributeListWriter attributeListWriterJson;
    private final DimensionListWriter dimensionListWriterJson;
    private final GroupDimensionListWriter groupDimensionListWriter;
    private final MeasureListWriter measureListWriterJson;
    private final DataStructure30To21ComponentAdapter dsdComponentAdapter;
    private final TimeDimensionLocalRepresentationAdapter timeDimensionLocalRepresentationAdapter;


    public DataStructureDefinitionWriter(VersionableWriter versionableWriter,
                                         LinksWriter linksWriter,
                                         AttributeListWriter attributeListWriterJson,
                                         DimensionListWriter dimensionListWriterJson,
                                         MeasureListWriter measureListWriterJson,
                                         GroupDimensionListWriter groupDimensionListWriter,
                                         DataStructure30To21ComponentAdapter dsdComponentAdapter,
                                         TimeDimensionLocalRepresentationAdapter timeDimensionLocalRepresentationAdapter) {
        super(versionableWriter, linksWriter);
        this.attributeListWriterJson = attributeListWriterJson;
        this.dimensionListWriterJson = dimensionListWriterJson;
        this.measureListWriterJson = measureListWriterJson;
        this.groupDimensionListWriter = groupDimensionListWriter;
        this.dsdComponentAdapter = dsdComponentAdapter;
        this.timeDimensionLocalRepresentationAdapter = timeDimensionLocalRepresentationAdapter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, DataStructureDefinition dsd) throws IOException {
        super.writeFields(jsonGenerator, dsd);
        writeDataStructureComponents(jsonGenerator, dsd);
    }

    @Override
    protected Set<DataStructureDefinition> extractArtefacts(Artefacts artefacts) {
        return artefacts.getDataStructures();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.DATA_STRUCTURES;
    }

    private void writeDataStructureComponents(JsonGenerator jsonGenerator, DataStructureDefinition dsd) throws IOException {
        dsd = timeDimensionLocalRepresentationAdapter.adapt(dsd);
        dsd = dsdComponentAdapter.recompose(dsd);
        jsonGenerator.writeFieldName(StructureUtils.DATA_STRUCTURE_COMPONENTS);
        AttributeDescriptor attributeDescriptor = dsd.getAttributeDescriptor();
        DimensionDescriptor dimensionDescriptor = dsd.getDimensionDescriptor();
        MeasureDescriptor measureDescriptor = dsd.getMeasureDescriptor();
        List<GroupDimensionDescriptor> groupDimensionDescriptors = dsd.getGroupDimensionDescriptors();
        if (DataStructureComponentsUtils.areAllEmpty(
            attributeDescriptor,
            dimensionDescriptor,
            measureDescriptor
        )) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeStartObject();
            measureListWriterJson.write(jsonGenerator, measureDescriptor);
            dimensionListWriterJson.write(jsonGenerator, dimensionDescriptor);
            groupDimensionListWriter.write(jsonGenerator, groupDimensionDescriptors);
            attributeListWriterJson.write(jsonGenerator, attributeDescriptor);
            jsonGenerator.writeEndObject();
        }
    }

}
