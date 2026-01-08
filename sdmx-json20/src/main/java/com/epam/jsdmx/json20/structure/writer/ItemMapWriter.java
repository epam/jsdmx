package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.ItemMap;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;

import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.collections.CollectionUtils;

public class ItemMapWriter {

    private final ReferenceAdapter referenceAdapter;

    protected ItemMapWriter(ReferenceAdapter referenceAdapter) {
        this.referenceAdapter = referenceAdapter;
    }

    protected <T extends ItemMap> void writeItemMaps(JsonGenerator jsonGenerator,
                                                     List<T> itemMaps,
                                                     AnnotableWriter annotableWriter) throws IOException {
        if (CollectionUtils.isNotEmpty(itemMaps)) {
            jsonGenerator.writeFieldName(StructureUtils.ITEM_MAPS);
            jsonGenerator.writeStartArray();
            for (ItemMap itemMap : itemMaps) {
                if (itemMap != null) {

                    jsonGenerator.writeStartObject();

                    annotableWriter.write(jsonGenerator, itemMap);

                    String source = itemMap.getSource();
                    writeSource(jsonGenerator, source);

                    String target = itemMap.getTarget();
                    writeTarget(jsonGenerator, target);

                    Instant validFrom = itemMap.getValidFrom();
                    writeValidFrom(jsonGenerator, validFrom);

                    Instant validTo = itemMap.getValidTo();
                    writeValidTo(jsonGenerator, validTo);

                    jsonGenerator.writeEndObject();
                }
            }
            jsonGenerator.writeEndArray();
        }
    }

    protected void writeSource(JsonGenerator jsonGenerator, ArtefactReference source) throws IOException {
        if (source != null) {
            jsonGenerator.writeStringField(StructureUtils.SOURCE, referenceAdapter.toAdaptedUrn(source));
        }
    }

    private void writeSource(JsonGenerator jsonGenerator, String source) throws IOException {
        if (source != null) {
            jsonGenerator.writeStringField(StructureUtils.SOURCE_VALUE, source);
        }
    }

    protected void writeTarget(JsonGenerator jsonGenerator, ArtefactReference target) throws IOException {
        if (target != null) {
            jsonGenerator.writeStringField(StructureUtils.TARGET, referenceAdapter.toAdaptedUrn(target));
        }
    }

    private void writeTarget(JsonGenerator jsonGenerator, String target) throws IOException {
        if (target != null) {
            jsonGenerator.writeStringField(StructureUtils.TARGET_VALUE, target);
        }
    }

    private void writeValidTo(JsonGenerator jsonGenerator, Instant validTo) throws IOException {
        if (validTo != null) {
            jsonGenerator.writeStringField(
                StructureUtils.VALID_TO,
                StructureUtils.mapInstantToString(validTo)
            );
        }
    }

    private void writeValidFrom(JsonGenerator jsonGenerator, Instant validFrom) throws IOException {
        if (validFrom != null) {
            jsonGenerator.writeStringField(
                StructureUtils.VALID_FROM,
                StructureUtils.mapInstantToString(validFrom)
            );
        }
    }
}
