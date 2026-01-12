package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEmptyOrNullElementText;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEndingTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.moveToNextTag;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.AfterPeriodImpl;
import com.epam.jsdmx.infomodel.sdmx30.BeforePeriodImpl;
import com.epam.jsdmx.infomodel.sdmx30.LocalisedMemberValueImpl;
import com.epam.jsdmx.infomodel.sdmx30.MemberSelection;
import com.epam.jsdmx.infomodel.sdmx30.MemberSelectionImpl;
import com.epam.jsdmx.infomodel.sdmx30.MemberValueImpl;
import com.epam.jsdmx.infomodel.sdmx30.RangePeriodImpl;
import com.epam.jsdmx.infomodel.sdmx30.SelectionValue;
import com.epam.jsdmx.infomodel.sdmx30.TimeRangePeriodImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import org.apache.commons.lang3.StringUtils;

public class MemberSelectionReader {

    public MemberSelection getMemberSelection(XMLStreamReader reader) throws XMLStreamException {
        var memberSelection = new MemberSelectionImpl();

        memberSelection.setIncluded(XmlReaderUtils.getIncluded(reader));
        memberSelection.setRemovePrefix(XmlReaderUtils.getRemovePrefix(reader));
        memberSelection.setComponentId(XmlReaderUtils.getId(reader));

        List<SelectionValue> selectionValues = getSelectionValues(reader);
        memberSelection.setSelectionValues(selectionValues);
        return memberSelection;
    }

    public List<SelectionValue> getSelectionValues(XMLStreamReader reader) throws XMLStreamException {
        List<SelectionValue> selectionValues = new ArrayList<>();
        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.COMPONENT, XmlConstants.KEY_VALUE)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.VALUE:
                    selectionValues.add(getValue(reader));
                    break;
                case XmlConstants.TIME_RANGE:
                    selectionValues.add(getTimeRange(reader));
                    break;
                default:
                    throw new IllegalArgumentException("MemberSelection " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
        return selectionValues;
    }

    private SelectionValue getTimeRange(XMLStreamReader reader) throws XMLStreamException {
        String validTo = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.VALID_TO);
        String validFrom = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.VALID_FROM);
        var rangePeriod = new RangePeriodImpl();

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.TIME_RANGE)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.BEFORE_PERIOD:
                    var beforePeriod = new BeforePeriodImpl();
                    setTimeRangePeriod(reader, beforePeriod, validTo, validFrom);
                    moveToNextTag(reader);
                    return beforePeriod;
                case XmlConstants.AFTER_PERIOD:
                    var afterPeriod = new AfterPeriodImpl();
                    setTimeRangePeriod(reader, afterPeriod, validTo, validFrom);
                    moveToNextTag(reader);
                    return afterPeriod;
                case XmlConstants.START_PERIOD:
                    rangePeriod.setStartPeriod(getTimeRangePeriod(reader, validTo, validFrom));
                    break;
                case XmlConstants.END_PERIOD:
                    rangePeriod.setEndPeriod(getTimeRangePeriod(reader, validTo, validFrom));
                    break;
                default:
                    throw new IllegalArgumentException("TimeRange " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
        rangePeriod.setValidFrom(parseDate(validFrom));
        rangePeriod.setValidTo(parseDate(validTo));
        return rangePeriod;
    }

    private TimeRangePeriodImpl getTimeRangePeriod(XMLStreamReader reader, String validTo, String validFrom) throws XMLStreamException {
        var timeRangePeriod = new TimeRangePeriodImpl();
        String isInclusive = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.IS_INCLUSIVE);
        if (isNotEmptyOrNullElementText(isInclusive)) {
            timeRangePeriod.setInclusive(Boolean.parseBoolean(isInclusive));
            String period = reader.getElementText();
            if (isNotEmptyOrNullElementText(period)) {
                timeRangePeriod.setPeriod(period);
            }
            timeRangePeriod.setValidTo(parseDate(validTo));
            timeRangePeriod.setValidFrom(parseDate(validFrom));
        }
        return timeRangePeriod;
    }

    private void setTimeRangePeriod(XMLStreamReader reader, TimeRangePeriodImpl beforePeriod, String validTo, String validFrom) throws XMLStreamException {
        String isInclusive = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.IS_INCLUSIVE);
        if (isNotEmptyOrNullElementText(isInclusive)) {
            beforePeriod.setInclusive(Boolean.parseBoolean(isInclusive));
            beforePeriod.setValidTo(parseDate(validTo));
            beforePeriod.setValidFrom(parseDate(validFrom));
            String period = reader.getElementText();
            if (isNotEmptyOrNullElementText(period)) {
                beforePeriod.setPeriod(period);
            }
        }
    }

    private SelectionValue getValue(XMLStreamReader reader) throws XMLStreamException {
        var memberValue = new MemberValueImpl();
        var localisedMemberValue = new LocalisedMemberValueImpl();
        String validFrom = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.VALID_FROM);
        String validTo = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.VALID_TO);
        String cascadeValues = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.CASCADE_VALUES);
        String local = reader.getAttributeValue(XmlConstants.XML_1998_NAMESPACE, XmlConstants.LANG);
        memberValue.setCascadeValue(XmlConstants.STRING_CASCADE_VALUE_TYPE.get(cascadeValues));

        if (isNotEmptyOrNullElementText(validTo)) {
            memberValue.setValidTo(parseDate(validTo));
            localisedMemberValue.setValidTo(parseDate(validTo));
        }

        if (isNotEmptyOrNullElementText(validFrom)) {
            memberValue.setValidFrom(parseDate(validFrom));
            localisedMemberValue.setValidFrom(parseDate(validFrom));
        }

        String value = reader.getElementText();
        memberValue.setValue(value);
        localisedMemberValue.setValue(value);

        if (local != null) {
            localisedMemberValue.setLocale(local);
            return localisedMemberValue;
        }
        return memberValue;
    }

    private Instant parseDate(String date) {
        try {
            return Instant.parse(date);
        } catch (DateTimeParseException ex) {
            return null;
        }
    }
}
