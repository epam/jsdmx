package com.epam.jsdmx.xml30.structure.writer;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ReleaseCalendar;

public class ReleaseCalenderWriter {

    public void writeReleaseCalendar(XMLStreamWriter writer, ReleaseCalendar releaseCalendar) throws XMLStreamException {
        if (releaseCalendar != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.RELEASE_CALENDAR);

            String periodicity = releaseCalendar.getPeriodicity();
            writeReleaseCalendarComponent(writer, periodicity, XmlConstants.PERIODICITY);

            String offset = releaseCalendar.getOffset();
            writeReleaseCalendarComponent(writer, offset, XmlConstants.OFFSET);

            String tolerance = releaseCalendar.getTolerance();
            writeReleaseCalendarComponent(writer, tolerance, XmlConstants.TOLERANCE);

            writer.writeEndElement();
        }
    }

    private void writeReleaseCalendarComponent(XMLStreamWriter writer,
                                               String component,
                                               String componentName) throws XMLStreamException {
        if (component != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + componentName);
            writer.writeCharacters(component);
            writer.writeEndElement();
        }
    }
}
