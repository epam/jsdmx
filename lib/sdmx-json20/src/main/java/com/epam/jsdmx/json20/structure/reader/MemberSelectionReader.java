package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName;
import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.getBooleanJsonField;
import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.getFieldAsString;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.AfterPeriodImpl;
import com.epam.jsdmx.infomodel.sdmx30.BeforePeriodImpl;
import com.epam.jsdmx.infomodel.sdmx30.CascadeValue;
import com.epam.jsdmx.infomodel.sdmx30.LocalisedMemberValueImpl;
import com.epam.jsdmx.infomodel.sdmx30.MemberSelection;
import com.epam.jsdmx.infomodel.sdmx30.MemberSelectionImpl;
import com.epam.jsdmx.infomodel.sdmx30.MemberValueImpl;
import com.epam.jsdmx.infomodel.sdmx30.RangePeriodImpl;
import com.epam.jsdmx.infomodel.sdmx30.SelectionValue;
import com.epam.jsdmx.infomodel.sdmx30.TimeRangePeriodImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.apache.commons.collections.CollectionUtils;

public class MemberSelectionReader {

    public MemberSelection getMemberSelection(JsonParser parser) {
        try {
            MemberSelectionImpl memberSelection = new MemberSelectionImpl();
            List<SelectionValue> selectionValues = new ArrayList<>();
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.INCLUDE:
                        memberSelection.setIncluded(getBooleanJsonField(parser));
                        break;
                    case StructureUtils.ID:
                        memberSelection.setComponentId(getFieldAsString(parser));
                        break;
                    case StructureUtils.REMOVE_PREFIX:
                        memberSelection.setRemovePrefix(getBooleanJsonField(parser));
                        break;
                    case StructureUtils.TIME_RANGE:
                        List<SelectionValue> timeRanges = getTimeRanges(parser);
                        if (CollectionUtils.isNotEmpty(timeRanges)) {
                            selectionValues.addAll(timeRanges);
                        }
                        break;
                    case StructureUtils.VALUES:
                        List<SelectionValue> values = ReaderUtils.getArray(parser, (this::getMemberValue));
                        selectionValues.addAll(values);
                        break;
                    default:
                        throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "MemberSelection: " + fieldName);
                }
            }
            memberSelection.setSelectionValues(selectionValues);
            return memberSelection;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    public SelectionValue getMemberValue(JsonParser parser) {
        try {
            MemberValueImpl memberValue = new MemberValueImpl();
            LocalisedMemberValueImpl localMemberValue = new LocalisedMemberValueImpl();
            parser.nextToken();
            if (parser.currentToken()
                .equals(JsonToken.START_OBJECT) && parser.nextToken()
                .equals(JsonToken.END_OBJECT)) {
                return null;
            }
            while (parser.currentToken() != JsonToken.END_OBJECT) {
                checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.VALUE:
                        String value = getFieldAsString(parser);
                        memberValue.setValue(value);
                        localMemberValue.setValue(value);
                        parser.nextToken();
                        break;
                    case StructureUtils.LANG:
                        String lang = getFieldAsString(parser);
                        if (lang != null) {
                            localMemberValue.setLocale(lang);
                        }
                        parser.nextToken();
                        break;
                    case StructureUtils.CASCADE_VALUES:
                        String cascadeValString = getFieldAsString(parser);
                        if (cascadeValString != null) {
                            CascadeValue cascadeValue = StructureUtils.STRING_CASCADE_VALUE_TYPE.get(cascadeValString);
                            memberValue.setCascadeValue(cascadeValue);
                        }
                        parser.nextToken();
                        break;
                    case StructureUtils.VALID_FROM:
                        Instant validFrom = ReaderUtils.getInstantObj(parser);
                        if (validFrom != null) {
                            memberValue.setValidFrom(validFrom);
                            localMemberValue.setValidFrom(validFrom);
                        }
                        parser.nextToken();
                        break;
                    case StructureUtils.VALID_TO:
                        Instant validTo = ReaderUtils.getInstantObj(parser);
                        if (validTo != null) {
                            memberValue.setValidTo(validTo);
                            localMemberValue.setValidTo(validTo);
                        }
                        parser.nextToken();
                        break;
                    default:
                        throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "MemberValue: " + fieldName);
                }
            }
            return localMemberValue.getLocale() != null ? localMemberValue : memberValue;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    public List<SelectionValue> getTimeRanges(JsonParser parser) {
        try {
            parser.nextToken();
            if (parser.currentToken()
                .equals(JsonToken.START_OBJECT) && parser.nextToken()
                .equals(JsonToken.END_OBJECT)) {
                return List.of();
            }
            List<SelectionValue> timeRanges = new ArrayList<>();
            RangePeriodImpl rangePeriod = new RangePeriodImpl();
            while (parser.currentToken() != JsonToken.END_OBJECT) {
                checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.AFTER_PERIOD:
                        AfterPeriodImpl afterPeriod = new AfterPeriodImpl();
                        setTimeRange(parser, afterPeriod);
                        timeRanges.add(afterPeriod);
                        parser.nextToken();
                        break;
                    case StructureUtils.BEFORE_PERIOD:
                        BeforePeriodImpl beforePeriod = new BeforePeriodImpl();
                        setTimeRange(parser, beforePeriod);
                        timeRanges.add(beforePeriod);
                        parser.nextToken();
                        break;
                    case StructureUtils.START_PERIOD:
                        TimeRangePeriodImpl startPeriod = new TimeRangePeriodImpl();
                        setTimeRange(parser, startPeriod);
                        rangePeriod.setStartPeriod(startPeriod);
                        parser.nextToken();
                        break;
                    case StructureUtils.END_PERIOD:
                        TimeRangePeriodImpl endPeriod = new TimeRangePeriodImpl();
                        setTimeRange(parser, endPeriod);
                        rangePeriod.setEndPeriod(endPeriod);
                        parser.nextToken();
                        break;
                    default:
                        throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "TimeRange: " + fieldName);
                }
            }
            if (!(rangePeriod.getStartPeriod() == null && rangePeriod.getEndPeriod() == null)) {
                timeRanges.add(rangePeriod);
            }
            return timeRanges;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    private void setTimeRange(JsonParser parser, TimeRangePeriodImpl timeRange) throws IOException {
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String fieldName = parser.getCurrentName();
            switch (fieldName) {
                case StructureUtils.IS_INCLUSIVE:
                    timeRange.setInclusive(getBooleanJsonField(parser));
                    break;
                case StructureUtils.PERIOD:
                    timeRange.setPeriod(getFieldAsString(parser));
                    break;
                default:
                    throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + " TimeRange: " + fieldName);
            }
        }
    }
}
