package com.epam.jsdmx.json10.structure.writer;

import java.io.IOException;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Code;

import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.collections4.CollectionUtils;

public interface CodeWriter<T extends Code> {

    default void writeCodes(JsonGenerator jsonGenerator,
                            List<? extends T> codes,
                            String name,
                            NameableWriter nameableWriter) throws IOException {
        if (CollectionUtils.isNotEmpty(codes)) {
            jsonGenerator.writeFieldName(name);
            jsonGenerator.writeStartArray();
            for (T code : codes) {
                write(jsonGenerator, code, nameableWriter);
            }
            jsonGenerator.writeEndArray();
        }
    }

    default void write(JsonGenerator jsonGenerator,
                       T code,
                       NameableWriter nameableWriter) throws IOException {
        jsonGenerator.writeStartObject();
        writeFields(jsonGenerator, code, nameableWriter);
        jsonGenerator.writeEndObject();
    }

    default void writeFields(JsonGenerator jsonGenerator,
                             T code,
                             NameableWriter nameableWriter) throws IOException {
        nameableWriter.write(jsonGenerator, code);
        if (code.getParentId() != null) {
            jsonGenerator.writeStringField(StructureUtils.PARENT, code.getParentId());
        }
    }
}
