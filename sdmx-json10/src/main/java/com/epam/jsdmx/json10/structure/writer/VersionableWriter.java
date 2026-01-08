package com.epam.jsdmx.json10.structure.writer;

import static com.epam.jsdmx.serializer.util.DateTimeConverterUtil.convertToDateTime;

import java.io.IOException;

import com.epam.jsdmx.infomodel.sdmx30.VersionableArtefact;

import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.lang3.StringUtils;

public class VersionableWriter {

    private final NameableWriter nameableWriter;

    public VersionableWriter(NameableWriter nameableWriter) {
        this.nameableWriter = nameableWriter;
    }

    public void write(JsonGenerator jsonGenerator, VersionableArtefact artefact) throws IOException {
        nameableWriter.write(jsonGenerator, artefact);
        jsonGenerator.writeStringField(StructureUtils.VERSION, artefact.getVersion().toString());
        String validTo = convertToDateTime(artefact.getValidToString());
        if (StringUtils.isNotBlank(validTo)) {
            jsonGenerator.writeStringField(StructureUtils.VALID_TO, validTo);
        }
        String validFrom = convertToDateTime(artefact.getValidFromString());
        if (StringUtils.isNotBlank(validFrom)) {
            jsonGenerator.writeStringField(StructureUtils.VALID_FROM, validFrom);
        }
    }
}
