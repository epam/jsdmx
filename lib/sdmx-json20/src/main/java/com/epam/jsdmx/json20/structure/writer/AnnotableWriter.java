package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.epam.jsdmx.infomodel.sdmx30.AnnotableArtefact;
import com.epam.jsdmx.infomodel.sdmx30.Annotation;

import com.fasterxml.jackson.core.JsonGenerator;

public class AnnotableWriter {

    private final LinksWriter linksWriter;
    private final List<Locale.LanguageRange> languagePriorities;

    public AnnotableWriter(LinksWriter linksWriter) {
        this.linksWriter = linksWriter;
        this.languagePriorities = List.of();
    }

    public AnnotableWriter(LinksWriter linksWriter, List<Locale.LanguageRange> languagePriorities) {
        this.linksWriter = linksWriter;
        this.languagePriorities = languagePriorities;
    }

    public void write(JsonGenerator jsonGenerator, AnnotableArtefact annotableArtefact) throws IOException {
        write(jsonGenerator, annotableArtefact.getAnnotations());
    }

    protected void write(JsonGenerator jsonGenerator, List<Annotation> annotations) throws IOException {
        if (!annotations.isEmpty()) {
            jsonGenerator.writeFieldName(StructureUtils.ANNOTATIONS);
            writeArray(jsonGenerator, annotations);
        } else {
            jsonGenerator.writeFieldName(StructureUtils.ANNOTATIONS);
            jsonGenerator.writeStartArray();
            jsonGenerator.writeEndArray();
        }
    }

    public void writeArray(JsonGenerator jsonGenerator, List<Annotation> annotations) throws IOException {
        jsonGenerator.writeStartArray();
        for (Annotation annotation : annotations) {
            writeAnnotation(jsonGenerator, annotation);
        }
        jsonGenerator.writeEndArray();
    }

    protected void writeAnnotation(JsonGenerator jsonGenerator, Annotation annotation) throws IOException {
        jsonGenerator.writeStartObject();
        if (annotation.getId() != null) {
            jsonGenerator.writeStringField(StructureUtils.ID, annotation.getId());
        }
        if (annotation.getTitle() != null) {
            jsonGenerator.writeStringField(StructureUtils.TITLE, annotation.getTitle());
        }
        if (annotation.getType() != null) {
            jsonGenerator.writeStringField(StructureUtils.TYPE, annotation.getType());
        }
        if (annotation.getValue() != null) {
            jsonGenerator.writeStringField(StructureUtils.VALUE, annotation.getValue());
        }
        final var i18nText = annotation.getText();
        if (i18nText != null) {
            jsonGenerator.writeStringField(StructureUtils.TEXT, StructureUtils.getLocalisedValue(i18nText, languagePriorities));
            StructureUtils.writeInternationalString(jsonGenerator, i18nText, StructureUtils.TEXTS);
        }
        linksWriter.writeLinks(jsonGenerator, annotation.getUrl());
        jsonGenerator.writeEndObject();
    }
}
