package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;

import com.epam.jsdmx.infomodel.sdmx30.GridCode;

import com.fasterxml.jackson.core.JsonGenerator;

public class GridCodeWriter extends CodeWriter<GridCode> {

    @Override
    public void writeFields(JsonGenerator jsonGenerator, GridCode code, NameableWriter nameableWriter) throws IOException {
        super.writeFields(jsonGenerator, code, nameableWriter);
        if (code.getGeoCell() != null) {
            jsonGenerator.writeStringField(StructureUtils.GEO_CELL, code.getGeoCell());
        }
    }
}
