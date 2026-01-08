package com.epam.jsdmx.json10.structure.writer;


import java.io.IOException;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.GroupDimensionDescriptor;

import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.collections4.CollectionUtils;

public class GroupDimensionListWriter {

    private final IdentifiableWriter identifiableWriter;

    public GroupDimensionListWriter(IdentifiableWriter identifiableWriter) {
        this.identifiableWriter = identifiableWriter;
    }

    public void write(JsonGenerator jsonGenerator, List<GroupDimensionDescriptor> descriptors) throws IOException {
        if (CollectionUtils.isEmpty(descriptors)) {
            return;
        }

        jsonGenerator.writeArrayFieldStart(StructureUtils.GROUPS);

        for (GroupDimensionDescriptor descriptor : descriptors) {
            jsonGenerator.writeStartObject();
            identifiableWriter.write(jsonGenerator, descriptor);

            jsonGenerator.writeFieldName(StructureUtils.GROUP_DIMENSIONS);
            String[] dimensionsStringList = descriptor.getDimensions().toArray(new String[0]);
            jsonGenerator.writeArray(dimensionsStringList, 0, dimensionsStringList.length);

            jsonGenerator.writeEndObject();
        }

        jsonGenerator.writeEndArray();
    }
}
