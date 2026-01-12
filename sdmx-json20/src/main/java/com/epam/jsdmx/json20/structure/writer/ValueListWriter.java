package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.ValueItem;
import com.epam.jsdmx.infomodel.sdmx30.ValueList;

import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.collections.CollectionUtils;

public class ValueListWriter extends MaintainableWriter<ValueList> {

    private final AnnotableWriter annotableWriter;

    public ValueListWriter(VersionableWriter versionableWriter,
                           LinksWriter linksWriter,
                           AnnotableWriter annotableWriter) {
        super(versionableWriter, linksWriter);
        this.annotableWriter = annotableWriter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, ValueList artefact) throws IOException {
        super.writeFields(jsonGenerator, artefact);
        List<ValueItem> items = artefact.getItems();
        if (CollectionUtils.isNotEmpty(items)) {
            jsonGenerator.writeFieldName(StructureUtils.VALUE_ITEMS);
            jsonGenerator.writeStartArray();

            for (ValueItem valueItem : items) {
                if (valueItem != null) {
                    jsonGenerator.writeStartObject();
                    annotableWriter.write(jsonGenerator, valueItem);

                    String id = valueItem.getId();
                    if (id != null) {
                        jsonGenerator.writeStringField(StructureUtils.ID, id);
                    }

                    InternationalString name = valueItem.getName();
                    writeName(jsonGenerator, artefact, name);

                    InternationalString description = valueItem.getDescription();
                    writeDescription(jsonGenerator, description);

                    jsonGenerator.writeEndObject();
                }
            }
            jsonGenerator.writeEndArray();
        }
    }

    @Override
    protected Set<ValueList> extractArtefacts(Artefacts artefacts) {
        return artefacts.getValueLists();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.VALUE_LISTS;
    }

    private void writeDescription(JsonGenerator jsonGenerator, InternationalString description) throws IOException {
        if (description != null) {
            jsonGenerator.writeStringField(
                StructureUtils.DESCRIPTION,
                description.getForDefaultLocale()
            );
            StructureUtils.writeInternationalString(jsonGenerator, description, StructureUtils.DESCRIPTIONS);
        }
    }

    private void writeName(JsonGenerator jsonGenerator, ValueList artefact, InternationalString name) throws IOException {
        if (name != null) {
            jsonGenerator.writeStringField(StructureUtils.NAME, artefact.getName().getForDefaultLocale());
            StructureUtils.writeInternationalString(jsonGenerator, name, StructureUtils.NAMES);
        }
    }
}