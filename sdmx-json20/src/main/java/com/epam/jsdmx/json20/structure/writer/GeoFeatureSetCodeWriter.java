package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;

import com.epam.jsdmx.infomodel.sdmx30.GeoFeatureSetCode;

import com.fasterxml.jackson.core.JsonGenerator;

public class GeoFeatureSetCodeWriter extends CodeWriter<GeoFeatureSetCode> {

    @Override
    public void writeFields(JsonGenerator jsonGenerator,
                            GeoFeatureSetCode code,
                            NameableWriter nameableWriter) throws IOException {
        super.writeFields(jsonGenerator, code, nameableWriter);
        String value = code.getValue();
        jsonGenerator.writeStringField(StructureUtils.VALUE, value);
    }
}
