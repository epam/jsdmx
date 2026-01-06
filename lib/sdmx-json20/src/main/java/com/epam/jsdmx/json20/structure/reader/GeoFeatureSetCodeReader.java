package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;

import com.epam.jsdmx.infomodel.sdmx30.GeoFeatureSetCodeImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;

public class GeoFeatureSetCodeReader implements CodeReader<GeoFeatureSetCodeImpl> {
    @Override
    public void readExtendedCodeFields(JsonParser parser, GeoFeatureSetCodeImpl code) throws IOException {
        String currentName = parser.getCurrentName();
        if (StructureUtils.VALUE.equals(currentName)) {
            code.setValue(ReaderUtils.getStringJsonField(parser));
        }
    }
}
