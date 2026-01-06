package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;

import com.epam.jsdmx.infomodel.sdmx30.ReleaseCalendar;
import com.epam.jsdmx.infomodel.sdmx30.ReleaseCalendarImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class ReleaseCalendarReader {

    public ReleaseCalendar getReleaseCalendar(JsonParser parser) throws IOException {
        ReleaseCalendarImpl releaseCalendar = new ReleaseCalendarImpl();
        while (!JsonToken.END_OBJECT.equals(parser.nextToken())) {
            ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String fieldName = parser.getCurrentName();
            switch (fieldName) {
                case StructureUtils.OFFSET:
                    releaseCalendar.setOffset(ReaderUtils.getFieldAsString(parser));
                    break;
                case StructureUtils.TOLERANCE:
                    releaseCalendar.setTolerance(ReaderUtils.getFieldAsString(parser));
                    break;
                case StructureUtils.PERIODICITY:
                    releaseCalendar.setPeriodicity(ReaderUtils.getFieldAsString(parser));
                    break;
                default:
                    throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + " ReleaseCalendar: " + fieldName);
            }
        }
        return releaseCalendar;
    }
}
