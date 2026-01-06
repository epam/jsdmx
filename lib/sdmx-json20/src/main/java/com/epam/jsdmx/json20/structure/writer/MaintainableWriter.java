package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefact;
import com.epam.jsdmx.infomodel.sdmx30.StructureClass;
import com.epam.jsdmx.json20.structure.reader.JsonRuntimeException;

import com.fasterxml.jackson.core.JsonGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class MaintainableWriter<T extends MaintainableArtefact> {

    private final VersionableWriter versionableWriter;
    private final LinksWriter linksWriter;

    public void write(JsonGenerator jsonGenerator, MaintainableArtefact artefact) throws IOException {
        jsonGenerator.writeStartObject();
        writeFields(jsonGenerator, (T) artefact);
        jsonGenerator.writeEndObject();
    }

    protected void writeFields(JsonGenerator jsonGenerator, T artefact) throws IOException {
        linksWriter.writeLinks(jsonGenerator, artefact.getLinks());
        versionableWriter.write(jsonGenerator, artefact);
        if (artefact.getOrganizationId() != null) {
            jsonGenerator.writeStringField(StructureUtils.AGENCY_ID, artefact.getOrganizationId());
        }
    }

    public void writeAndClose(JsonGenerator jsonGenerator, T maintainableArtefact) {
        try (jsonGenerator) {
            write(jsonGenerator, maintainableArtefact);
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    public Optional<StructureClass> getWritableArtefactStructureClass() {
        // if there is a need to find writer by its maintainable artefact type
        return Optional.empty();
    }

    protected Set<T> extractArtefacts(Artefacts artefacts) {
        //different for writers with stream and the ones with dto
        return Collections.emptySet();
    }

    protected String getArrayName() {
        //different for writers with stream and the ones with dto
        return null;
    }
}
