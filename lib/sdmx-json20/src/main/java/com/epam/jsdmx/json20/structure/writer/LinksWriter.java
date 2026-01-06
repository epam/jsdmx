package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.InternationalUri;
import com.epam.jsdmx.infomodel.sdmx30.Link;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;

import com.fasterxml.jackson.core.JsonGenerator;

public class LinksWriter {

    private final ReferenceAdapter referenceAdapter;

    public LinksWriter(ReferenceAdapter referenceAdapter) {
        this.referenceAdapter = referenceAdapter;
    }

    public void writeLinks(JsonGenerator jsonGenerator, List<Link> links) throws IOException {
        // Don't write links for sdmx 3.0 artefacts
    }

    public void writeLinks(JsonGenerator jsonGenerator, InternationalUri uri) throws IOException {
        if (uri != null) {
            jsonGenerator.writeFieldName(StructureUtils.LINKS);
            jsonGenerator.writeStartArray();
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField(StructureUtils.URI, uri.getForDefaultLocale().toString());
            jsonGenerator.writeEndObject();
            jsonGenerator.writeEndArray();
        }
    }
}
