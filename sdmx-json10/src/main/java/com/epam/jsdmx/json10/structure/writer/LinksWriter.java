package com.epam.jsdmx.json10.structure.writer;

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
        jsonGenerator.writeFieldName(StructureUtils.LINKS);
        jsonGenerator.writeStartArray();
        if (links != null && !links.isEmpty()) {
            for (Link link : links) {
                writeLink(jsonGenerator, link);
            }
        }
        jsonGenerator.writeEndArray();
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

    private void writeLink(JsonGenerator jsonGenerator, Link link) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField(StructureUtils.URN, referenceAdapter.adaptUrn(link.getUrn()));
        jsonGenerator.writeStringField(StructureUtils.TYPE, referenceAdapter.adaptType(link.getType()));
        jsonGenerator.writeStringField(StructureUtils.REL, link.getRel());
        jsonGenerator.writeEndObject();
    }
}
