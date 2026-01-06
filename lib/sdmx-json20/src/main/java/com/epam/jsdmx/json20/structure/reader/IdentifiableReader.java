package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;

import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefact;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;

public class IdentifiableReader {

    private final AnnotableReader annotableReader;

    public IdentifiableReader(AnnotableReader annotableReader) {
        this.annotableReader = annotableReader;
    }

    public IdentifiableArtefact read(IdentifiableArtefactImpl identifiableArtefact, JsonParser parser) throws IOException {
        ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
        String fieldName = parser.getCurrentName();
        if (StructureUtils.ID.equals(fieldName)) {
            identifiableArtefact.setId(ReaderUtils.getStringJsonField(parser));
        } else {
            annotableReader.read(identifiableArtefact, parser);
        }
        return identifiableArtefact;
    }
}
