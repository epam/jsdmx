package com.epam.jsdmx.json20.structure.writer;

import java.util.List;
import java.util.Locale;
import java.util.Locale.LanguageRange;

import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.Party;
import com.epam.jsdmx.serializer.sdmx30.common.DefaultHeaderProvider;
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
        if (header.getSchema() != null) {
            jsonGenerator.writeStringField(StructureUtils.SCHEMA, header.getSchema().toString());
        }
        if (header.getId() != null) {
            jsonGenerator.writeStringField(StructureUtils.ID, header.getId());
        }
        Boolean isTest = header.isTest();
        if (isTest != null) {
            jsonGenerator.writeBooleanField(StructureUtils.TEST, isTest);
        }
        if (header.getPrepared() != null) {
            jsonGenerator.writeStringField(StructureUtils.PREPARED, header.getPrepared().toString());
        }
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
        if (party.getId() != null) {
            jsonGenerator.writeStringField(StructureUtils.ID, party.getId());
        }
        if (party.getName() != null) {
            jsonGenerator.writeStringField(StructureUtils.NAME, party.getName().getForDefaultLocale());
        }
        jsonGenerator.writeEndObject();
    }
}
