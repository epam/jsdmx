package com.epam.jsdmx.xml30.structure.writer;

import static com.epam.jsdmx.xml30.structure.writer.XmlWriterUtils.writeInternationalString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Facet;
import com.epam.jsdmx.infomodel.sdmx30.FacetType;
import com.epam.jsdmx.infomodel.sdmx30.FacetValueType;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.Representation;
import com.epam.jsdmx.infomodel.sdmx30.SentinelValue;
import com.epam.jsdmx.infomodel.sdmx30.TextFormatImpl;

public class RepresentationWriter {

    private final UrnWriter urnWriter;

    public RepresentationWriter(UrnWriter urnWriter) {
        this.urnWriter = urnWriter;
    }

    private void writeEnumerated(XMLStreamWriter writer, Representation coreRepresentation) throws XMLStreamException {
        ArtefactReference artefactReference = coreRepresentation.enumerated();
        if (artefactReference != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.ENUMERATION);
            ArtefactReference maintainableArtefactReference = artefactReference.getMaintainableArtefactReference();
            if (maintainableArtefactReference != null) {
                urnWriter.writeUrnCharacters(maintainableArtefactReference, writer);
            }
            writer.writeEndElement();

        }
    }

    public void writeRepresentation(XMLStreamWriter writer, Representation representation, String representationName) throws XMLStreamException {
        if (representation != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + representationName);
            boolean enumerated = representation.isEnumerated();
            if (enumerated) {
                writeEnumerated(writer, representation);
            } else {
                writeTextFormat(writer, representation);
            }
            writer.writeEndElement();
        }
    }

    private void writeTextFormat(XMLStreamWriter writer, Representation coreRepresentation) throws XMLStreamException {
        writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.TEXT_FORMAT);
        Set<Facet> facets = coreRepresentation.nonEnumerated();
        if (!facets.isEmpty()) {
            TextFormatImpl textFormat = new TextFormatImpl(facets);
            writeTextFormatAttribute(writer, textFormat);

            writeSentinelValues(writer, textFormat);
        }
        writer.writeEndElement();
    }

    private void writeSentinelValues(XMLStreamWriter writer, TextFormatImpl textFormat) throws XMLStreamException {
        if (textFormat.hasSentinelValues()) {
            Optional<List<SentinelValue>> sentinelValues = textFormat.getSentinelValues();
            if (sentinelValues.isPresent()) {
                List<SentinelValue> sentinelValueList = sentinelValues.get();
                for (SentinelValue sentinelValue : sentinelValueList) {
                    writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.SENTINEL_VALUE);
                    String value = sentinelValue.getValue();
                    if (value != null) {
                        writer.writeAttribute("value", value);
                        InternationalString name = sentinelValue.getName();
                        writeInternationalString(name, writer, XmlConstants.COMMON + XmlConstants.COM_NAME);
                        InternationalString description = sentinelValue.getDescription();
                        writeInternationalString(description, writer, XmlConstants.COMMON + XmlConstants.COM_DESCRIPTION);
                    }
                    writer.writeEndElement();
                }
            }
        }
    }

    private void writeTextFormatAttribute(XMLStreamWriter writer, TextFormatImpl textFormat) throws XMLStreamException {

        if (textFormat.hasValueType()) {
            Optional<FacetValueType> valueType = textFormat.getValueType();
            writer.writeAttribute(XmlConstants.TEXT_TYPE, valueType.get().value());
        }

        if (textFormat.hasDecimals()) {
            Optional<BigInteger> decimals = textFormat.getDecimals();
            if (decimals.isPresent()) {
                writer.writeAttribute(XmlConstants.MAP_FACET_TYPE.get(FacetType.DECIMALS.name()), String.valueOf(decimals.get()));
            }
            return;
        }

        if (textFormat.hasEndTime()) {
            Optional<Instant> endTime = textFormat.getEndTime();
            if (endTime.isPresent()) {
                writer.writeAttribute(XmlConstants.MAP_FACET_TYPE.get(FacetType.END_TIME.name()), String.valueOf(endTime.get()));
            }
            return;
        }

        if (textFormat.hasInterval()) {
            Optional<BigDecimal> interval = textFormat.getInterval();
            if (interval.isPresent()) {
                writer.writeAttribute(XmlConstants.MAP_FACET_TYPE.get(FacetType.INTERVAL.name()), String.valueOf(interval.get()));
            }
            return;
        }

        if (textFormat.hasEndValue()) {
            Optional<BigDecimal> endValue = textFormat.getEndValue();
            if (endValue.isPresent()) {
                writer.writeAttribute(XmlConstants.MAP_FACET_TYPE.get(FacetType.END_VALUE.name()), String.valueOf(endValue.get()));
            }
            return;
        }

        if (textFormat.hasIsMultiLingual()) {
            Optional<Boolean> isMultiLingual = textFormat.getIsMultiLingual();
            if (isMultiLingual.isPresent()) {
                writer.writeAttribute(XmlConstants.MAP_FACET_TYPE.get(FacetType.IS_MULTILINGUAL.name()), String.valueOf(isMultiLingual.get()));
            }
            return;
        }

        if (textFormat.hasIsSequence()) {
            Optional<Boolean> isSequence = textFormat.getIsSequence();
            if (isSequence.isPresent()) {
                writer.writeAttribute(XmlConstants.MAP_FACET_TYPE.get(FacetType.IS_SEQUENCE.name()), String.valueOf(isSequence.get()));
            }
            return;
        }

        if (textFormat.hasMaxValue()) {
            Optional<BigDecimal> maxValue = textFormat.getMaxValue();
            if (maxValue.isPresent()) {
                writer.writeAttribute(XmlConstants.MAP_FACET_TYPE.get(FacetType.MAX_VALUE.name()), String.valueOf(maxValue.get()));
            }
            return;
        }

        if (textFormat.hasMinValue()) {
            Optional<BigDecimal> minValue = textFormat.getMinValue();
            if (minValue.isPresent()) {
                writer.writeAttribute(XmlConstants.MAP_FACET_TYPE.get(FacetType.MIN_VALUE.name()), String.valueOf(minValue.get()));
            }
            return;
        }

        if (textFormat.hasMaxLength()) {
            Optional<BigInteger> maxLength = textFormat.getMaxLength();
            if (maxLength.isPresent()) {
                writer.writeAttribute(XmlConstants.MAP_FACET_TYPE.get(FacetType.MAX_LENGTH.name()), String.valueOf(maxLength.get()));
            }
            return;
        }

        if (textFormat.hasMinLength()) {
            Optional<BigInteger> minLength = textFormat.getMinLength();
            if (minLength.isPresent()) {
                writer.writeAttribute(XmlConstants.MAP_FACET_TYPE.get(FacetType.MIN_LENGTH.name()), String.valueOf(minLength.get()));
            }
            return;
        }

        if (textFormat.hasStartTime()) {
            Optional<Instant> startTime = textFormat.getStartTime();
            if (startTime.isPresent()) {
                writer.writeAttribute(XmlConstants.MAP_FACET_TYPE.get(FacetType.START_TIME.name()), String.valueOf(startTime.get()));
            }
            return;
        }

        if (textFormat.hasStartValue()) {
            Optional<BigDecimal> startValue = textFormat.getStartValue();
            if (startValue.isPresent()) {
                writer.writeAttribute(XmlConstants.MAP_FACET_TYPE.get(FacetType.START_VALUE.name()), String.valueOf(startValue.get()));
            }
            return;
        }

        if (textFormat.hasPattern()) {
            Optional<String> pattern = textFormat.getPattern();
            if (pattern.isPresent()) {
                writer.writeAttribute(XmlConstants.MAP_FACET_TYPE.get(FacetType.PATTERN.name()), pattern.get());
            }
            return;
        }

        if (textFormat.hasTimeInterval()) {
            Optional<String> timeInterval = textFormat.getTimeInterval();
            if (timeInterval.isPresent()) {
                writer.writeAttribute(XmlConstants.MAP_FACET_TYPE.get(FacetType.TIME_INTERVAL.name()), timeInterval.get());
            }
        }
    }
}