package com.epam.jsdmx.json10.structure.writer;

import java.io.IOException;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.MemberSelection;
import com.epam.jsdmx.infomodel.sdmx30.MetadataTargetRegion;

import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.collections4.CollectionUtils;

public class MetadataConstraintWriter {
    private final SelectionValueWriter selectionValueWriter;

    public MetadataConstraintWriter(SelectionValueWriter selectionValueWriter) {
        this.selectionValueWriter = selectionValueWriter;
    }

    public void writeMetadataTargetRegions(JsonGenerator gen, List<MetadataTargetRegion> metadataTargetRegions) throws IOException {
        if (CollectionUtils.isNotEmpty(metadataTargetRegions)) {
            gen.writeFieldName(StructureUtils.METADATA_TARGET_REGIONS);
            gen.writeStartArray();
            for (MetadataTargetRegion metadataTargetRegion : metadataTargetRegions) {
                if (metadataTargetRegion != null) {
                    gen.writeStartObject();
                    gen.writeBooleanField(StructureUtils.INCLUDE, metadataTargetRegion.isIncluded());
                    writeMemberSelections(gen, metadataTargetRegion.getMemberSelections());
                    gen.writeEndObject();
                }
            }
            gen.writeEndArray();
        }
    }

    private void writeMemberSelections(JsonGenerator generator, List<MemberSelection> memberSelections) throws IOException {
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

}
