package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.ComponentValue;
import com.epam.jsdmx.infomodel.sdmx30.DataKey;
import com.epam.jsdmx.infomodel.sdmx30.DataKeySet;

import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.collections.CollectionUtils;

public class DataKeySetsWriter {
    private final AnnotableWriter annotableWriter;
    private final MemberSelectionWriter memberSelectionWriter;
    private final DateTimeWriter dateTimeWriter;

    public DataKeySetsWriter(AnnotableWriter annotableWriter, MemberSelectionWriter memberSelectionWriter, DateTimeWriter dateTimeWriter) {
        this.annotableWriter = annotableWriter;
        this.memberSelectionWriter = memberSelectionWriter;
        this.dateTimeWriter = dateTimeWriter;
    }

    public void write(JsonGenerator jsonGenerator, List<DataKeySet> dataKeySets) throws IOException {
        if (CollectionUtils.isNotEmpty(dataKeySets)) {
            jsonGenerator.writeFieldName(StructureUtils.DATA_KEY_SETS);
            jsonGenerator.writeStartArray();
            for (DataKeySet dataKeySet : dataKeySets) {
                if (dataKeySet != null) {
                    jsonGenerator.writeStartObject();

                    jsonGenerator.writeBooleanField(StructureUtils.IS_INCLUDED, dataKeySet.isIncluded());

                    writeDataKeys(jsonGenerator, dataKeySet.getKeys());

                    jsonGenerator.writeEndObject();
                }
            }
            jsonGenerator.writeEndArray();
        }
    }

    public void writeDataKeys(JsonGenerator jsonGenerator, List<DataKey> keys) throws IOException {
        if (CollectionUtils.isNotEmpty(keys)) {
            jsonGenerator.writeFieldName(StructureUtils.KEYS);
            jsonGenerator.writeStartArray();
            for (DataKey dataKey : keys) {
                if (dataKey != null) {
                    jsonGenerator.writeStartObject();

                    annotableWriter.write(jsonGenerator, dataKey);

                    writeKeyValues(jsonGenerator, dataKey.getKeyValues());
                    memberSelectionWriter.writeMemberSelections(jsonGenerator, dataKey.getMemberSelections());
                    jsonGenerator.writeBooleanField(StructureUtils.INCLUDE, dataKey.isIncluded());
                    dateTimeWriter.writeValidDate(jsonGenerator, dataKey.getValidFrom(), StructureUtils.VALID_FROM);
                    dateTimeWriter.writeValidDate(jsonGenerator, dataKey.getValidTo(), StructureUtils.VALID_TO);

                    jsonGenerator.writeEndObject();
                }
            }
            jsonGenerator.writeEndArray();
        }
    }

    public void writeKeyValues(JsonGenerator jsonGenerator, List<ComponentValue> keyValues) throws IOException {
        if (CollectionUtils.isNotEmpty(keyValues)) {
            jsonGenerator.writeFieldName(StructureUtils.KEY_VALUES);
            jsonGenerator.writeStartArray();
            for (ComponentValue componentValue : keyValues) {
                if (componentValue != null) {
                    jsonGenerator.writeStartObject();

                    if (componentValue.getComponentId() != null) {
                        jsonGenerator.writeStringField(StructureUtils.ID, componentValue.getComponentId());
                    }
                    jsonGenerator.writeBooleanField(StructureUtils.INCLUDE, componentValue.isIncluded());
                    jsonGenerator.writeBooleanField(StructureUtils.REMOVE_PREFIX, componentValue.isRemovePrefix());
                    if (componentValue.getValue() != null) {
                        jsonGenerator.writeStringField(StructureUtils.VALUE, componentValue.getValue());
                    }

                    jsonGenerator.writeEndObject();
                }
            }
            jsonGenerator.writeEndArray();
        }
    }

}
