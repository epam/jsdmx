package com.epam.jsdmx.xml30.structure.writer;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.GridCode;

public class GridCodeWriter extends CodeWriter<GridCode> {

    protected GridCodeWriter(UrnWriter urnWriter, NameableWriter nameableWriter, AnnotableWriter annotableWriter) {
        super(urnWriter, nameableWriter, annotableWriter);
    }

    @Override
    public void writeElements(GridCode code,
                              XMLStreamWriter writer) throws XMLStreamException {
        super.writeElements(code, writer);
        String geoCell = code.getGeoCell();
        if (geoCell != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.GEO_CELL);
            writer.writeCharacters(geoCell);
            writer.writeEndElement();
        }
    }
}
