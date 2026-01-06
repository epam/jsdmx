package com.epam.jsdmx.json10.structure.writer;

import java.io.IOException;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.ComponentValue;
import com.epam.jsdmx.infomodel.sdmx30.DataKey;
import com.epam.jsdmx.infomodel.sdmx30.DataKeySet;

import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.collections4.CollectionUtils;

public class DataKeySetsWriter {

    public void write(JsonGenerator generator, List<DataKeySet> dataKeySets) throws IOException {
        if (CollectionUtils.isNotEmpty(dataKeySets)) {
            generator.writeFieldName(StructureUtils.DATA_KEY_SETS);
            generator.writeStartArray();
            for (DataKeySet dataKeySet : dataKeySets) {
                if (dataKeySet != null) {
                    generator.writeStartObject();

                    generator.writeBooleanField(StructureUtils.IS_INCLUDED, dataKeySet.isIncluded());

                    writeDataKeys(generator, dataKeySet.getKeys());

                    generator.writeEndObject();
                }
            }
            generator.writeEndArray();
        }
    }


    private void writeDataKeys(JsonGenerator jsonGenerator, List<DataKey> keys) throws IOException {
        if (CollectionUtils.isNotEmpty(keys)) {
            jsonGenerator.writeFieldName(StructureUtils.KEYS);
            jsonGenerator.writeStartArray();
            for (DataKey dataKey : keys) {
                if (dataKey != null) {
                    jsonGenerator.writeStartObject();
                    writeKeyValues(jsonGenerator, dataKey.getKeyValues());
                    jsonGenerator.writeEndObject();
                }
            }
            jsonGenerator.writeEndArray();
        }
    }

    private void writeKeyValues(JsonGenerator jsonGenerator, List<ComponentValue> keyValues) throws IOException {
        if (CollectionUtils.isNotEmpty(keyValues)) {
            jsonGenerator.writeFieldName(StructureUtils.KEY_VALUES);
            jsonGenerator.writeStartArray();
            for (ComponentValue componentValue : keyValues) {
                if (componentValue != null) {
                    jsonGenerator.writeStartObject();

                    jsonGenerator.writeStringField(StructureUtils.ID, componentValue.getComponentId());
                    jsonGenerator.writeStringField(StructureUtils.VALUE, componentValue.getValue());

                    jsonGenerator.writeEndObject();
                }
            }
            jsonGenerator.writeEndArray();
        }
    }
}
