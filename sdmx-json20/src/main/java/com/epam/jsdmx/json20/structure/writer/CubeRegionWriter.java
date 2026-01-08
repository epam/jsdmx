package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.CubeRegion;
import com.epam.jsdmx.infomodel.sdmx30.CubeRegionKey;

import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.collections4.CollectionUtils;

public class CubeRegionWriter {
    private final MemberSelectionWriter memberSelectionWriter;
    private final DateTimeWriter dateTimeWriter;

    private final AnnotableWriter annotableWriter;

    public CubeRegionWriter(MemberSelectionWriter memberSelectionWriter,
                            DateTimeWriter dateTimeWriter,
                            AnnotableWriter annotableWriter) {
        this.memberSelectionWriter = memberSelectionWriter;
        this.dateTimeWriter = dateTimeWriter;
        this.annotableWriter = annotableWriter;
    }

    public void write(JsonGenerator jsonGenerator, List<CubeRegion> cubeRegions) throws IOException {
        jsonGenerator.writeFieldName(StructureUtils.CUBE_REGIONS);
        jsonGenerator.writeStartArray();
        if (CollectionUtils.isNotEmpty(cubeRegions)) {
            for (CubeRegion cubeRegion : cubeRegions) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeBooleanField(StructureUtils.INCLUDE, cubeRegion.isIncluded());
                memberSelectionWriter.writeMemberSelections(jsonGenerator, cubeRegion.getMemberSelections());
                writeKeyValues(jsonGenerator, cubeRegion.getCubeRegionKeys());
                annotableWriter.write(jsonGenerator, cubeRegion);
                jsonGenerator.writeEndObject();
            }
        }
        jsonGenerator.writeEndArray();
    }


    private void writeKeyValues(JsonGenerator jsonGenerator, List<CubeRegionKey> cubeRegionKeys) throws IOException {
        jsonGenerator.writeFieldName(StructureUtils.KEY_VALUES);
        jsonGenerator.writeStartArray();
        if (CollectionUtils.isNotEmpty(cubeRegionKeys)) {
            for (CubeRegionKey cubeRegionKey : cubeRegionKeys) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(StructureUtils.ID, cubeRegionKey.getComponentId());
                jsonGenerator.writeBooleanField(StructureUtils.INCLUDE, cubeRegionKey.isIncluded());
                jsonGenerator.writeBooleanField(StructureUtils.REMOVE_PREFIX, cubeRegionKey.isRemovePrefix());
                dateTimeWriter.writeValidDate(jsonGenerator, cubeRegionKey.getValidFrom(), StructureUtils.VALID_FROM);
                dateTimeWriter.writeValidDate(jsonGenerator, cubeRegionKey.getValidTo(), StructureUtils.VALID_TO);
                memberSelectionWriter.writeSelectionValues(jsonGenerator, cubeRegionKey.getSelectionValues());
                jsonGenerator.writeEndObject();
            }
        }
        jsonGenerator.writeEndArray();
    }
}
