package com.epam.jsdmx.json10.structure.writer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.epam.jsdmx.infomodel.sdmx30.AfterPeriod;
import com.epam.jsdmx.infomodel.sdmx30.BeforePeriod;
import com.epam.jsdmx.infomodel.sdmx30.LocalisedMemberValue;
import com.epam.jsdmx.infomodel.sdmx30.MemberValue;
import com.epam.jsdmx.infomodel.sdmx30.RangePeriod;
import com.epam.jsdmx.infomodel.sdmx30.SelectionValue;
import com.epam.jsdmx.infomodel.sdmx30.TimeRangePeriod;
import com.epam.jsdmx.infomodel.sdmx30.TimeRangeValue;

import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;

public class SelectionValueWriter {

    public void writeSelectionValues(JsonGenerator jsonGenerator, List<SelectionValue> selectionValues) throws IOException {
        if (CollectionUtils.isNotEmpty(selectionValues)) {
            List<SelectionValue> timeRanges = selectionValues.stream().filter(Objects::nonNull)
                .filter(TimeRangeValue.class::isInstance)
                .collect(Collectors.toList());
            writeTimeRanges(jsonGenerator, timeRanges);

            List<SelectionValue> values = selectionValues.stream().filter(Objects::nonNull)
                .filter(value -> !(value instanceof TimeRangeValue))
                .collect(Collectors.toList());
            writeValues(jsonGenerator, values);

            //TODO cascadeValues in question
        }
    }

    private void writeTimeRanges(JsonGenerator jsonGenerator, List<SelectionValue> timeRanges) throws IOException {
        if (CollectionUtils.isNotEmpty(timeRanges)) {
            List<Pair<TimeRangePeriod, String>> values = new ArrayList<>();
            for (SelectionValue timeRange : timeRanges) {
                if (timeRange != null) {
                    if (timeRange instanceof AfterPeriod) {
                        AfterPeriod afterPeriod = (AfterPeriod) timeRange;
                        values.add(Pair.of(afterPeriod, StructureUtils.AFTER_PERIOD));
                    }
                    if (timeRange instanceof BeforePeriod) {
                        BeforePeriod beforePeriod = (BeforePeriod) timeRange;
                        values.add(Pair.of(beforePeriod, StructureUtils.BEFORE_PERIOD));
                    }
                    if (timeRange instanceof RangePeriod) {
                        RangePeriod rangePeriod = (RangePeriod) timeRange;
                        TimeRangePeriod startPeriod = rangePeriod.getStartPeriod();
                        if (startPeriod != null) {
                            values.add(Pair.of(startPeriod, StructureUtils.START_PERIOD));
                        }
                        TimeRangePeriod endPeriod = rangePeriod.getEndPeriod();
                        if (endPeriod != null) {
                            values.add(Pair.of(endPeriod, StructureUtils.END_PERIOD));
                        }
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(values)) {
                jsonGenerator.writeFieldName(StructureUtils.TIME_RANGE);
                jsonGenerator.writeStartObject();
                for (Pair<TimeRangePeriod, String> value : values) {
                    writeTimeRangePeriod(jsonGenerator, value.getLeft(), value.getRight());
                }
                jsonGenerator.writeEndObject();
            }
        }
    }

    public void writeTimeRangePeriod(JsonGenerator jsonGenerator,
                                     TimeRangePeriod timeRangePeriod,
                                     String timeRangePeriodName) throws IOException {
        String period = timeRangePeriod.getPeriod();
        boolean inclusive = timeRangePeriod.isInclusive();
        jsonGenerator.writeFieldName(timeRangePeriodName);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField(StructureUtils.PERIOD, period);
        jsonGenerator.writeBooleanField(StructureUtils.IS_INCLUSIVE, inclusive);
        jsonGenerator.writeEndObject();
    }

    private void writeValues(JsonGenerator jsonGenerator, List<SelectionValue> memberValues) throws IOException {
        if (CollectionUtils.isNotEmpty(memberValues)) {
            jsonGenerator.writeFieldName(StructureUtils.VALUES);
            jsonGenerator.writeStartArray();
            for (SelectionValue selectionValue : memberValues) {
                if (selectionValue instanceof MemberValue) {
                    MemberValue memberValue = (MemberValue) selectionValue;
                    jsonGenerator.writeString(memberValue.getValue());
                }
                if (selectionValue instanceof LocalisedMemberValue) {
                    LocalisedMemberValue memberValue = (LocalisedMemberValue) selectionValue;
                    jsonGenerator.writeString(memberValue.getValue());
                }
            }
            jsonGenerator.writeEndArray();
        }
    }
}
