package com.epam.jsdmx.json10.structure.writer;

import java.io.IOException;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.AnnotableArtefact;
import com.epam.jsdmx.infomodel.sdmx30.Annotation;

import com.fasterxml.jackson.core.JsonGenerator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

@RequiredArgsConstructor
public class AnnotableWriter {

    private final LinksWriter linksWriter;

    public <T extends AnnotableArtefact> void write(JsonGenerator jsonGenerator, T annotableArtefact) throws IOException {
        write(jsonGenerator, annotableArtefact.getAnnotations());
    }

    protected void write(JsonGenerator jsonGenerator, List<Annotation> annotations) throws IOException {
        if (CollectionUtils.isEmpty(annotations)) {
            return;
        }
        jsonGenerator.writeFieldName(StructureUtils.ANNOTATIONS);
        jsonGenerator.writeStartArray();
        for (Annotation annotation : annotations) {
            writeAnnotation(jsonGenerator, annotation);
        }
        jsonGenerator.writeEndArray();
    }

    private void writeAnnotation(JsonGenerator jg, Annotation annotation) throws IOException {
        jg.writeStartObject();
        StructureUtils.writeStringField(jg, StructureUtils.ID, annotation.getId());
        StructureUtils.writeStringField(jg, StructureUtils.TITLE, annotation.getTitle());
        StructureUtils.writeStringField(jg, StructureUtils.TYPE, annotation.getType());
        StructureUtils.writeStringField(jg, StructureUtils.TEXT, (annotation.getText() != null) ? annotation.getText().getForDefaultLocale() : null);
        StructureUtils.writeInternationalString(jg, annotation.getText(), StructureUtils.TEXTS);
        linksWriter.writeLinks(jg, annotation.getUrl());
        jg.writeEndObject();
    }
}
