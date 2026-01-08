package com.epam.jsdmx.json20.structure.reader;


import java.io.IOException;

import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactImpl;
import com.epam.jsdmx.infomodel.sdmx30.Version;
import com.epam.jsdmx.infomodel.sdmx30.VersionableArtefact;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;

public class VersionableReader {

    private final NameableReader nameableReader;

    public VersionableReader(NameableReader nameableReader) {
        this.nameableReader = nameableReader;
    }

    public VersionableArtefact read(MaintainableArtefactImpl maintainableArtefact, JsonParser parser) throws IOException {
        ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
        String fieldname = parser.getCurrentName();
        switch (fieldname) {
            case StructureUtils.VERSION:
                maintainableArtefact.setVersion(Version.createFromString(ReaderUtils.getStringJsonField(parser)));
                break;
            case StructureUtils.VALID_TO:
                maintainableArtefact.setValidTo(ReaderUtils.getStringJsonField(parser));
                break;
            case StructureUtils.VALID_FROM:
                maintainableArtefact.setValidFrom(ReaderUtils.getStringJsonField(parser));
                break;
            default:
                nameableReader.read(maintainableArtefact, parser);
                break;
        }
        return maintainableArtefact;
    }

}
