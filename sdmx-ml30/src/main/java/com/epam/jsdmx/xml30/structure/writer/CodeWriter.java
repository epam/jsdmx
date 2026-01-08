package com.epam.jsdmx.xml30.structure.writer;

import java.net.URI;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Code;

public abstract class CodeWriter<T extends Code> {

    private final UrnWriter urnWriter;
    private final NameableWriter nameableWriter;
    private final AnnotableWriter annotableWriter;

    protected CodeWriter(UrnWriter urnWriter, NameableWriter nameableWriter, AnnotableWriter annotableWriter) {
        this.urnWriter = urnWriter;
        this.nameableWriter = nameableWriter;
        this.annotableWriter = annotableWriter;
    }

    protected void write(T code,
                         XMLStreamWriter writer,
                         String name) throws XMLStreamException {
        writer.writeStartElement(XmlConstants.STRUCTURE + name);
        writeAttributes(code, writer);
        writeElements(code, writer);
        writer.writeEndElement();
    }

    protected void writeAttributes(T code, XMLStreamWriter writer) throws XMLStreamException {
        String id = code.getId();
        if (id != null) {
            writer.writeAttribute(XmlConstants.ID, id);
        }

        URI uri = code.getUri();
        if (uri != null) {
            writer.writeAttribute(XmlConstants.URI, uri.toString());
        }

        if (code.getContainer() != null) {
            String urn = code.getUrn();
            if (urn != null) {
                urnWriter.writeUrn(urn, writer);
            }
        }
    }

    protected void writeElements(T code,
                                 XMLStreamWriter writer) throws XMLStreamException {
        String parentId = code.getParentId();
        annotableWriter.write(code, writer);
        nameableWriter.write(code, writer);

        if (parentId != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.STRUCTURE_PARENT);
            writer.writeCharacters(parentId);
            writer.writeEndElement();
        }
    }
}
