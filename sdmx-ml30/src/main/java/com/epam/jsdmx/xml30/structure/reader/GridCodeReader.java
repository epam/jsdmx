package com.epam.jsdmx.xml30.structure.reader;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.GridCodeImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

public class GridCodeReader implements CodeReader<GridCodeImpl> {

    @Override
    public void readCodeSpecialFields(XMLStreamReader reader, GridCodeImpl code, String localName) throws XMLStreamException {
        if (!XmlConstants.GEO_CELL.equals(localName)) {
            throw new IllegalArgumentException("GridCode does not support " + localName);
        }
        code.setGeoCell(reader.getElementText());
    }
}
