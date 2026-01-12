package com.epam.jsdmx.xml30.structure.writer;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefact;
import com.epam.jsdmx.serializer.sdmx30.structure.StructureWriter;

import com.sun.xml.txw2.output.IndentingXMLStreamWriter;
import lombok.SneakyThrows;

/**
 * Streaming serialization for xml (3.0)
 */
public class XmlStructureWriter implements StructureWriter {
    protected final HeaderWriter headerWriter;
    private final XMLStreamWriter writer;
    private final List<? extends XmlWriter<? extends MaintainableArtefact>> writerList;

    @SneakyThrows
    public XmlStructureWriter(OutputStream outputStream,
                              List<? extends XmlWriter<? extends MaintainableArtefact>> writerList,
                              HeaderWriter headerWriter,
                              boolean isPretty) {
        XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(
            new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        this.writer = isPretty ? new IndentingXMLStreamWriter(xmlStreamWriter) : xmlStreamWriter;
        this.writerList = List.copyOf(writerList);
        this.headerWriter = headerWriter;
    }

    @Override
    @SneakyThrows
    public void write(Artefacts artefacts) {
        try {
            writer.writeStartDocument();
            writer.writeStartElement(XmlConstants.MESSAGE + XmlConstants.MES_STRUCTURE);
            writeNameSpaces();
            writer.writeAttribute("xsi:schemaLocation", "http://www.sdmx.org/resources/sdmxml/schemas/v3_0/message ../../schemas/SDMXMessage.xsd");

            headerWriter.writeDefaultHeader(writer);

            writer.writeStartElement(XmlConstants.MESSAGE + XmlConstants.MES_STRUCTURES);

            writerList.forEach(xmlWriter -> {
                try {
                    Set<? extends MaintainableArtefact> maintainableArtefacts = xmlWriter.extractArtefacts(artefacts);
                    if (!maintainableArtefacts.isEmpty()) {
                        writer.writeStartElement(XmlConstants.STRUCTURE + xmlWriter.getNamePlural());
                        maintainableArtefacts.forEach(artefact -> {
                            try {
                                xmlWriter.write(artefact, writer);
                            } catch (XMLStreamException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        writer.writeEndElement();
                    }
                } catch (XMLStreamException e) {
                    throw new RuntimeException(e);
                }
            });

            writer.writeEndElement();

            writer.writeEndElement();
            writer.writeEndDocument();
            writer.close();
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeNameSpaces() throws XMLStreamException {
        writer.writeNamespace("message", XmlConstants.SCHEMAS_V_3_0_MESSAGE);
        writer.writeNamespace("structure", XmlConstants.SCHEMAS_V_3_0_STRUCTURE);
        writer.writeNamespace("common", XmlConstants.SCHEMAS_V_3_0_COMMON);
        writer.writeNamespace("xsi", XmlConstants.XMLSCHEMA_INSTANCE);
        writer.writeNamespace("xml", XmlConstants.XML_1998_NAMESPACE);
    }
}
