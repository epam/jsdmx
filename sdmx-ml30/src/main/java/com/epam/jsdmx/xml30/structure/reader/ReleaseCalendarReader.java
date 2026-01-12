package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEndingTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.moveToNextTag;

import java.util.Optional;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.ReleaseCalendarImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

public class ReleaseCalendarReader {

    public ReleaseCalendarImpl getReleaseCalendar(XMLStreamReader reader) throws XMLStreamException {
        var releaseCalendar = new ReleaseCalendarImpl();
        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.RELEASE_CALENDAR)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.PERIODICITY:
                    Optional.ofNullable(reader.getElementText())
                        .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                        .ifPresent(releaseCalendar::setPeriodicity);
                    break;
                case XmlConstants.OFFSET:
                    Optional.ofNullable(reader.getElementText())
                        .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                        .ifPresent(releaseCalendar::setOffset);
                    break;
                case XmlConstants.TOLERANCE:
                    Optional.ofNullable(reader.getElementText())
                        .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                        .ifPresent(releaseCalendar::setTolerance);
                    break;
                default:
                    throw new IllegalArgumentException("ReleaseCalendar " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
        return releaseCalendar;
    }
}