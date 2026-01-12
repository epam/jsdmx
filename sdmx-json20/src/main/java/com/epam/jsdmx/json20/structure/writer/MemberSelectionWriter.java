package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.epam.jsdmx.infomodel.sdmx30.CascadeValue;
import com.epam.jsdmx.infomodel.sdmx30.LocalisedMemberValue;
import com.epam.jsdmx.infomodel.sdmx30.MemberSelection;
import com.epam.jsdmx.infomodel.sdmx30.MemberValue;
import com.epam.jsdmx.infomodel.sdmx30.SelectionValue;
import com.epam.jsdmx.infomodel.sdmx30.TimeRangeValue;

import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.collections.CollectionUtils;

public class MemberSelectionWriter {
    private final DateTimeWriter dateTimeWriter;

    public MemberSelectionWriter(DateTimeWriter dateTimeWriter) {
        this.dateTimeWriter = dateTimeWriter;
    }

    public void writeMemberSelections(JsonGenerator jsonGenerator, List<MemberSelection> memberSelections) throws IOException {
        if (CollectionUtils.isNotEmpty(memberSelections)) {
            jsonGenerator.writeFieldName(StructureUtils.COMPONENTS);
            jsonGenerator.writeStartArray();
            for (MemberSelection memberSelection : memberSelections) {
                if (memberSelection != null) {
                    jsonGenerator.writeStartObject();

                    jsonGenerator.writeStringField(StructureUtils.ID, memberSelection.getComponentId());
                    jsonGenerator.writeBooleanField(StructureUtils.INCLUDE, memberSelection.isIncluded());
                    jsonGenerator.writeBooleanField(StructureUtils.REMOVE_PREFIX, memberSelection.isRemovePrefix());
                    writeSelectionValues(jsonGenerator, memberSelection.getSelectionValues());

                    jsonGenerator.writeEndObject();
                }
            }
            jsonGenerator.writeEndArray();
        }
    }

    public void writeSelectionValues(JsonGenerator jsonGenerator, List<SelectionValue> selectionValues) throws IOException {
        if (CollectionUtils.isNotEmpty(selectionValues)) {
            List<SelectionValue> timeRanges = selectionValues.stream().filter(Objects::nonNull)
                .filter(TimeRangeValue.class::isInstance)
                .collect(Collectors.toList());
            dateTimeWriter.writeTimeRanges(jsonGenerator, timeRanges);

            List<SelectionValue> memberValues = selectionValues.stream().filter(Objects::nonNull)
                .filter(value -> !(value instanceof TimeRangeValue))
                .collect(Collectors.toList());
            writeMemberValues(jsonGenerator, memberValues);
        }
    }

    private void writeMemberValues(JsonGenerator jsonGenerator, List<SelectionValue> memberValues) throws IOException {
        if (CollectionUtils.isNotEmpty(memberValues)) {
            jsonGenerator.writeFieldName(StructureUtils.VALUES);
            jsonGenerator.writeStartArray();
            for (SelectionValue selectionValue : memberValues) {
                jsonGenerator.writeStartObject();
                if (selectionValue instanceof MemberValue) {
                    MemberValue memberValue = (MemberValue) selectionValue;
                    jsonGenerator.writeStringField(StructureUtils.VALUE, memberValue.getValue());

                    CascadeValue cascadeValue = memberValue.getCascadeValue();
                    if (StructureUtils.CASCADE_VALUE_TYPE_STRING.get(cascadeValue) != null) {
                        jsonGenerator.writeStringField(StructureUtils.CASCADE_VALUES, StructureUtils.CASCADE_VALUE_TYPE_STRING.get(cascadeValue));
                    }
                }
                if (selectionValue instanceof LocalisedMemberValue) {
                    LocalisedMemberValue memberValue = (LocalisedMemberValue) selectionValue;
                    jsonGenerator.writeStringField(StructureUtils.VALUE, memberValue.getValue());

                    String locale = memberValue.getLocale();
                    if (locale != null) {
                        jsonGenerator.writeStringField(StructureUtils.LANG, locale);
                    }
                }
                dateTimeWriter.writeValidDate(jsonGenerator, selectionValue.getValidFrom(), StructureUtils.VALID_FROM);
                dateTimeWriter.writeValidDate(jsonGenerator, selectionValue.getValidTo(), StructureUtils.VALID_TO);

                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();

        }
    }
}
