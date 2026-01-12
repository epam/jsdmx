package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.Map;

import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.NameableArtefact;
import com.epam.jsdmx.infomodel.sdmx30.NameableArtefactImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;

public class NameableReader {

    private final IdentifiableReader identifiableReader;

    public NameableReader(IdentifiableReader identifiableReader) {
        this.identifiableReader = identifiableReader;
    }

    public NameableArtefact read(NameableArtefactImpl maintainableArtefact, JsonParser parser) throws IOException {
        ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.NAMES:
                Map<String, String> localizedNames = ReaderUtils.getLocalizedField(parser);
                if (localizedNames != null) {
                    maintainableArtefact.setName(new InternationalString(localizedNames));
                }
                break;
            case StructureUtils.DESCRIPTIONS:
                Map<String, String> localizedDescriptions = ReaderUtils.getLocalizedField(parser);
                if (localizedDescriptions != null) {
                    maintainableArtefact.setDescription(new InternationalString(localizedDescriptions));
                }
                break;
            case StructureUtils.DESCRIPTION:
            case StructureUtils.NAME:
                parser.nextToken();
                parser.skipChildren();
                break;
            default:
                identifiableReader.read(maintainableArtefact, parser);
                break;
        }
        return maintainableArtefact;
    }
}
