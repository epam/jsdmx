package com.epam.jsdmx.xml21.structure.writer;

import java.net.URI;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class CodeWriter<T extends Code> {

    private final ReferenceWriter referenceWriter;
    private final NameableWriter nameableWriter;
    private final AnnotableWriter annotableWriter;

    void write(T code,
               XMLStreamWriter writer,
               String name) throws XMLStreamException {
        writer.writeStartElement(XmlConstants.STR + name);
        writeAttributes(code, writer);
        writeElements(code, writer);
        writer.writeEndElement();
    }

    void writeAttributes(T code, XMLStreamWriter writer) throws XMLStreamException {
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
                referenceWriter.writeUrnAttribute(writer, urn);
            }
        }
    }

    void writeElements(T code,
                       XMLStreamWriter writer) throws XMLStreamException {
        String parentId = code.getParentId();
        annotableWriter.write(code, writer);
        nameableWriter.write(code, writer);

        if (parentId != null) {
            writer.writeStartElement(XmlConstants.STR + XmlConstants.STRUCTURE_PARENT);
            referenceWriter.writeLocalReference(writer, parentId);
            writer.writeEndElement();
        }
    }
}
