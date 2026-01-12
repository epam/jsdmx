package com.epam.jsdmx.json10.structure.writer;

import java.io.IOException;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.CubeRegion;
import com.epam.jsdmx.infomodel.sdmx30.CubeRegionKey;
import com.epam.jsdmx.infomodel.sdmx30.MemberSelection;

import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.collections4.CollectionUtils;

public class CubeRegionWriter {
    private final SelectionValueWriter selectionValueWriter;
    private final AnnotableWriter annotableWriter;

    public CubeRegionWriter(SelectionValueWriter selectionValueWriter, AnnotableWriter annotableWriter) {
        this.selectionValueWriter = selectionValueWriter;
        this.annotableWriter = annotableWriter;
    }

    public void write(JsonGenerator generator, List<CubeRegion> cubeRegions) throws IOException {
        generator.writeFieldName(StructureUtils.CUBE_REGIONS);
        generator.writeStartArray();
        if (CollectionUtils.isNotEmpty(cubeRegions)) {
            for (CubeRegion cubeRegion : cubeRegions) {
                generator.writeStartObject();
                generator.writeBooleanField(StructureUtils.IS_INCLUDED, cubeRegion.isIncluded());
                writeAttributes(generator, cubeRegion.getMemberSelections());
                writeKeyValues(generator, cubeRegion.getCubeRegionKeys());
                annotableWriter.write(generator, cubeRegion);
                generator.writeEndObject();
            }
        }
        generator.writeEndArray();
    }

    private void writeAttributes(JsonGenerator generator, List<MemberSelection> memberSelections) throws IOException {
        if (CollectionUtils.isNotEmpty(memberSelections)) {
            generator.writeFieldName(StructureUtils.ATTRIBUTES);
            generator.writeStartArray();
            for (MemberSelection memberSelection : memberSelections) {
                if (memberSelection != null) {
                    generator.writeStartObject();

                    generator.writeStringField(StructureUtils.ID, memberSelection.getComponentId());
                    selectionValueWriter.writeSelectionValues(generator, memberSelection.getSelectionValues());

                    generator.writeEndObject();
                }
            }
            generator.writeEndArray();
        }
    }

    private void writeKeyValues(JsonGenerator jsonGenerator, List<CubeRegionKey> cubeRegionKeys) throws IOException {
        jsonGenerator.writeFieldName(StructureUtils.KEY_VALUES);
        jsonGenerator.writeStartArray();
        if (CollectionUtils.isNotEmpty(cubeRegionKeys)) {
            for (CubeRegionKey cubeRegionKey : cubeRegionKeys) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(StructureUtils.ID, cubeRegionKey.getComponentId());
                selectionValueWriter.writeSelectionValues(jsonGenerator, cubeRegionKey.getSelectionValues());
                jsonGenerator.writeEndObject();
            }
        }
        jsonGenerator.writeEndArray();
    }
}
