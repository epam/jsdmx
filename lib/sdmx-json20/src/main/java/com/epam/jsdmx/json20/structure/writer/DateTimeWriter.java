package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.AfterPeriod;
import com.epam.jsdmx.infomodel.sdmx30.BeforePeriod;
import com.epam.jsdmx.infomodel.sdmx30.RangePeriod;
import com.epam.jsdmx.infomodel.sdmx30.SelectionValue;
import com.epam.jsdmx.infomodel.sdmx30.TimeRangePeriod;

import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;

public class DateTimeWriter {
    public void writeValidDate(JsonGenerator jsonGenerator,
                               Instant instant,
                               String instantName) throws IOException {
        if (instant != null) {
            jsonGenerator.writeStringField(
                instantName,
                StructureUtils.mapInstantToString(instant)
            );
        }
    }

    public void writeTimeRanges(JsonGenerator jsonGenerator, List<SelectionValue> timeRanges) throws IOException {
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
}
