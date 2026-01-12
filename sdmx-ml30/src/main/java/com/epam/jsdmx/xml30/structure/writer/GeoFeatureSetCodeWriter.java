package com.epam.jsdmx.xml30.structure.writer;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.GeoFeatureSetCodeImpl;

public class GeoFeatureSetCodeWriter extends CodeWriter<GeoFeatureSetCodeImpl> {

    protected GeoFeatureSetCodeWriter(UrnWriter urnWriter, NameableWriter nameableWriter, AnnotableWriter annotableWriter) {
        super(urnWriter, nameableWriter, annotableWriter);
    }

    @Override
    public void writeAttributes(GeoFeatureSetCodeImpl code, XMLStreamWriter writer) throws XMLStreamException {
        super.writeAttributes(code, writer);
        String value = code.getValue();
        if (value != null) {
            writer.writeAttribute(XmlConstants.VALUE_LOWER, value);
        }
    }
}
