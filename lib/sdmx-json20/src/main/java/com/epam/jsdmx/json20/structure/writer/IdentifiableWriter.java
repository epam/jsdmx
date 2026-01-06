package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;

import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefact;

import com.fasterxml.jackson.core.JsonGenerator;
import lombok.Data;

@Data
public class IdentifiableWriter {

    private AnnotableWriter annotableWriter;

    public IdentifiableWriter(AnnotableWriter annotableWriter) {
        this.annotableWriter = annotableWriter;
    }

    public void write(JsonGenerator jsonGenerator, IdentifiableArtefact artefact) throws IOException {
        annotableWriter.write(jsonGenerator, artefact);
        String id = artefact.getId();
        if (id != null) {
            jsonGenerator.writeStringField(StructureUtils.ID, id);
        }
    }
}
