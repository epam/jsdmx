package com.epam.jsdmx.xml30.structure.writer;

import static com.epam.jsdmx.xml30.structure.writer.XmlWriterUtils.writeCharacters;

import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.AfterPeriod;
import com.epam.jsdmx.infomodel.sdmx30.BeforePeriod;
import com.epam.jsdmx.infomodel.sdmx30.CascadeValue;
import com.epam.jsdmx.infomodel.sdmx30.LocalisedMemberValue;
import com.epam.jsdmx.infomodel.sdmx30.MemberSelection;
import com.epam.jsdmx.infomodel.sdmx30.MemberValue;
import com.epam.jsdmx.infomodel.sdmx30.MemberValueImpl;
import com.epam.jsdmx.infomodel.sdmx30.RangePeriod;
import com.epam.jsdmx.infomodel.sdmx30.RangePeriodImpl;
import com.epam.jsdmx.infomodel.sdmx30.SelectionValue;
import com.epam.jsdmx.infomodel.sdmx30.TimeRangePeriod;
import com.epam.jsdmx.infomodel.sdmx30.TimeRangeValue;

import org.apache.commons.collections.CollectionUtils;

public class MemberSelectionWriter {

    public void writeMemberSelections(XMLStreamWriter writer, List<MemberSelection> memberSelections) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(memberSelections)) {
            for (MemberSelection memberSelection : memberSelections) {
                writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.COMPONENT);

                boolean includedMember = memberSelection.isIncluded();
                writer.writeAttribute(XmlConstants.INCLUDE, String.valueOf(includedMember));

                String componentId = memberSelection.getComponentId();
                writer.writeAttribute(XmlConstants.ID, componentId);

                boolean removePrefix = memberSelection.isRemovePrefix();
                writer.writeAttribute(XmlConstants.REMOVE_PREFIX, String.valueOf(removePrefix));

                List<SelectionValue> selectionValues = memberSelection.getSelectionValues();
                writeSelectionValues(writer, selectionValues);

                writer.writeEndElement();
            }
        }
    }

    public void writeSelectionValues(XMLStreamWriter writer, List<SelectionValue> selectionValues) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(selectionValues)) {
            for (SelectionValue selectionValue : selectionValues) {
                if (selectionValue instanceof MemberValue) {
                    writeMemberValue(writer, (MemberValueImpl) selectionValue);
                } else if (selectionValue instanceof LocalisedMemberValue) {
                    writeLocalisedMemberValue(writer, (LocalisedMemberValue) selectionValue);
                } else if (selectionValue instanceof TimeRangeValue) {
                    writeTimeRange(writer, selectionValue);
                }
            }
        }
    }

    private void writeLocalisedMemberValue(XMLStreamWriter writer, LocalisedMemberValue localisedMemberValue) throws XMLStreamException {
        if (localisedMemberValue != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.VALUE);

            XmlWriterUtils.writeInstant(localisedMemberValue.getValidTo(), writer, XmlConstants.VALID_TO);
            XmlWriterUtils.writeInstant(localisedMemberValue.getValidFrom(), writer, XmlConstants.VALID_FROM);
            String locale = localisedMemberValue.getLocale();
            if (locale != null) {
                writer.writeAttribute(XmlConstants.XML_LANG, locale);
            }

            writeCharacters(localisedMemberValue.getValue(), writer);

            writer.writeEndElement();
        }
    }

    private void writeTimeRange(XMLStreamWriter writer, SelectionValue selectionValue) throws XMLStreamException {
        if (selectionValue != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.TIME_RANGE);

            XmlWriterUtils.writeInstant(selectionValue.getValidTo(), writer, XmlConstants.VALID_TO);
            XmlWriterUtils.writeInstant(selectionValue.getValidFrom(), writer, XmlConstants.VALID_FROM);

            if (selectionValue instanceof BeforePeriod) {
                BeforePeriod beforePeriod = (BeforePeriod) selectionValue;
                writeTimeRangePeriod(writer, beforePeriod, XmlConstants.BEFORE_PERIOD);
            } else if (selectionValue instanceof AfterPeriod) {
                AfterPeriod afterPeriod = (AfterPeriod) selectionValue;
                writeTimeRangePeriod(writer, afterPeriod, XmlConstants.AFTER_PERIOD);
            } else if (selectionValue instanceof RangePeriod) {
                RangePeriodImpl rangePeriod = (RangePeriodImpl) selectionValue;
                writeRangePeriod(rangePeriod.getStartPeriod(), writer, XmlConstants.START_PERIOD);
                writeRangePeriod(rangePeriod.getEndPeriod(), writer, XmlConstants.END_PERIOD);
            }

            writer.writeEndElement();
        }
    }

    private void writeRangePeriod(TimeRangePeriod rangePeriod, XMLStreamWriter writer, String periodName) throws XMLStreamException {
        if (rangePeriod != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + periodName);

            writer.writeAttribute(XmlConstants.IS_INCLUSIVE, String.valueOf(rangePeriod.isInclusive()));
            writeCharacters(rangePeriod.getPeriod(), writer);

            writer.writeEndElement();
        }
    }

    private void writeMemberValue(XMLStreamWriter writer, MemberValueImpl memberVal) throws XMLStreamException {
        if (memberVal != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.VALUE);

            XmlWriterUtils.writeInstant(memberVal.getValidTo(), writer, XmlConstants.VALID_TO);
            XmlWriterUtils.writeInstant(memberVal.getValidFrom(), writer, XmlConstants.VALID_FROM);
            CascadeValue cascadeValue = memberVal.getCascadeValue();
            if (cascadeValue != null) {
                writer.writeAttribute(XmlConstants.CASCADE_VALUES, XmlConstants.CASCADE_VALUE_TYPE_STRING.get(cascadeValue));
            }

            writeCharacters(memberVal.getValue(), writer);

            writer.writeEndElement();
        }
    }

    private void writeTimeRangePeriod(XMLStreamWriter writer,
                                      TimeRangePeriod selectionValue,
                                      String timeRangeName) throws XMLStreamException {
        writer.writeStartElement(XmlConstants.STRUCTURE + timeRangeName);

        boolean inclusive = selectionValue.isInclusive();
        writer.writeAttribute(XmlConstants.IS_INCLUSIVE, String.valueOf(inclusive));

        writeCharacters(selectionValue.getPeriod(), writer);

        writer.writeEndElement();
    }
}
