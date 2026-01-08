package com.epam.jsdmx.json20.structure.writer;

import static com.epam.jsdmx.json20.structure.writer.StructureUtils.writeInternationalString;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
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
        writeName(jsonGenerator, artefact);
        writeDescription(jsonGenerator, artefact);
    }

    private void writeName(JsonGenerator jsonGenerator, NameableArtefact artefact) throws IOException {
        writeI18nValue(artefact.getName(), jsonGenerator, StructureUtils.NAME, StructureUtils.NAMES);
    }

    private void writeDescription(JsonGenerator jsonGenerator, NameableArtefact artefact) throws IOException {
        writeI18nValue(artefact.getDescription(), jsonGenerator, StructureUtils.DESCRIPTION, StructureUtils.DESCRIPTIONS);
    }

    private void writeI18nValue(InternationalString i18nString,
                                JsonGenerator jsonGenerator,
                                String localisedFieldName,
                                String allNamesField) throws IOException {
        if (i18nString != null) {
            final String value = StructureUtils.getLocalisedValue(i18nString, languagePriorities);
            if (value != null) {
                jsonGenerator.writeStringField(localisedFieldName, value);
            }
            writeInternationalString(jsonGenerator, i18nString, allNamesField);
        }
    }
}
