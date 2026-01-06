package com.epam.jsdmx.json10.structure.writer;

import java.util.List;
import java.util.Locale;
import java.util.Locale.LanguageRange;

import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.Party;
import com.epam.jsdmx.serializer.sdmx30.common.Header;

import com.fasterxml.jackson.core.JsonGenerator;
import lombok.SneakyThrows;

public class MetaWriter {

    private final List<LanguageRange> languagePriorities;
    private final DefaultHeaderProvider defaultHeaderProvider;

    public MetaWriter(List<LanguageRange> languagePriorities, DefaultHeaderProvider defaultHeaderProvider) {
        this.languagePriorities = languagePriorities;
        this.defaultHeaderProvider = defaultHeaderProvider;
    }

    @SneakyThrows
    public void writeHeader(JsonGenerator jsonGenerator, Header header) {
        if (header == null) {
            return;
        }
        jsonGenerator.writeFieldName(StructureUtils.META);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField(StructureUtils.SCHEMA, header.getSchema().toString());
        jsonGenerator.writeStringField(StructureUtils.ID, header.getId());
        jsonGenerator.writeBooleanField(StructureUtils.TEST, header.isTest());
        jsonGenerator.writeStringField(StructureUtils.PREPARED, header.getPrepared().toString());
        jsonGenerator.writeArrayFieldStart(StructureUtils.CONTENT_LANGUAGES);
        for (Locale locale : header.getContentLanguages()) {
            jsonGenerator.writeString(locale.toString());
        }
        jsonGenerator.writeEndArray();

        final InternationalString name = header.getName();
        if (name != null) {
            jsonGenerator.writeStringField(StructureUtils.NAME, name.getForRanges(languagePriorities));
            StructureUtils.writeInternationalString(jsonGenerator, name, StructureUtils.NAMES);
        }

        writeParty(jsonGenerator, header.getSender());
        jsonGenerator.writeEndObject();
    }

    public void writeDefaultHeader(JsonGenerator jsonGenerator) {
        Header header = defaultHeaderProvider.provide();
        writeHeader(jsonGenerator, header);
    }

    @SneakyThrows
    private void writeParty(JsonGenerator jsonGenerator, Party party) {
        jsonGenerator.writeFieldName(StructureUtils.SENDER);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField(StructureUtils.ID, party.getId());
        if (party.getName() != null) {
            jsonGenerator.writeStringField(StructureUtils.NAME, party.getName().getForDefaultLocale());
        }
        jsonGenerator.writeEndObject();
    }
}
