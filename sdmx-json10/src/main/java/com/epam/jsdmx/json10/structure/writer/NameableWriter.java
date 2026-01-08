package com.epam.jsdmx.json10.structure.writer;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.epam.jsdmx.infomodel.sdmx30.NameableArtefact;

import com.fasterxml.jackson.core.JsonGenerator;

public class NameableWriter {

    private final IdentifiableWriter identifiableWriter;
    private List<Locale.LanguageRange> languagePriorities;

    public NameableWriter(IdentifiableWriter identifiableWriter) {
        this.identifiableWriter = identifiableWriter;
    }

    public NameableWriter(IdentifiableWriter identifiableWriter, List<Locale.LanguageRange> languagePriorities) {
        this.identifiableWriter = identifiableWriter;
        this.languagePriorities = languagePriorities;
    }

    public void write(JsonGenerator jsonGenerator, NameableArtefact artefact) throws IOException {
        identifiableWriter.write(jsonGenerator, artefact);
        if (artefact.getName() != null) {
            jsonGenerator.writeStringField(StructureUtils.NAME, artefact.getName().getForRanges(languagePriorities));
            StructureUtils.writeInternationalString(jsonGenerator, artefact.getName(), StructureUtils.NAMES);
        }
        if (artefact.getDescription() != null && !artefact.getDescription().isEmpty()) {
            jsonGenerator.writeStringField(
                StructureUtils.DESCRIPTION,
                artefact.getDescription().getForRanges(languagePriorities)
            );
            StructureUtils.writeInternationalString(jsonGenerator, artefact.getDescription(), StructureUtils.DESCRIPTIONS);
        }
    }
}
