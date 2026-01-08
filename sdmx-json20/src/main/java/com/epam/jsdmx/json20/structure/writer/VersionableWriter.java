package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;

import com.epam.jsdmx.infomodel.sdmx30.VersionableArtefact;

import com.fasterxml.jackson.core.JsonGenerator;

public class VersionableWriter {

    private final NameableWriter nameableWriter;

    public VersionableWriter(NameableWriter nameableWriter) {
        this.nameableWriter = nameableWriter;
    }

    public void write(JsonGenerator jsonGenerator, VersionableArtefact artefact) throws IOException {
        nameableWriter.write(jsonGenerator, artefact);
        jsonGenerator.writeStringField(StructureUtils.VERSION, artefact.getVersion().toString());
        String validTo = artefact.getValidToString();
        if (validTo != null) {
            jsonGenerator.writeStringField(StructureUtils.VALID_TO, validTo);
        }
        String validFrom = artefact.getValidFromString();
        if (validFrom != null) {
            jsonGenerator.writeStringField(StructureUtils.VALID_FROM, validFrom);
        }
    }
}
