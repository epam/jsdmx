package com.epam.jsdmx.xml30.structure.writer;

import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Link;

public class LinksWriter {

    private final UrnWriter urnWriter;

    public LinksWriter(UrnWriter urnWriter) {
        this.urnWriter = urnWriter;
    }

    public void write(List<Link> links, XMLStreamWriter writer) throws XMLStreamException {
        // Don't write links for sdmx 3.0 artefacts
    }
}
