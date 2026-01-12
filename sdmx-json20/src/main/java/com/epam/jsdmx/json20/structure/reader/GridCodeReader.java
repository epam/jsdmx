package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;

import com.epam.jsdmx.infomodel.sdmx30.GridCodeImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;

public class GridCodeReader implements CodeReader<GridCodeImpl> {

    @Override
    public void readExtendedCodeFields(JsonParser parser, GridCodeImpl code) throws IOException {
        String currentName = parser.getCurrentName();
        if (StructureUtils.GEO_CELL.equals(currentName)) {
            code.setGeoCell(ReaderUtils.getStringJsonField(parser));
        }
    }
}
