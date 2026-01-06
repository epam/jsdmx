package com.epam.jsdmx.xml21.structure.writer;

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
 * Streaming serialization for xml 2.1
 */
public class Xml21StructureWriter implements StructureWriter {
    private final XMLStreamWriter writer;
    protected final HeaderWriter headerWriter;

    private final List<? extends XmlWriter<? extends MaintainableArtefact>> writerList;

    @SneakyThrows
    public Xml21StructureWriter(OutputStream outputStream,
                                List<? extends XmlWriter<? extends MaintainableArtefact>> writerList,
                                HeaderWriter headerWriter,
                                boolean isPretty) {
        XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance()
            .createXMLStreamWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
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
            writer.writeAttribute("xsi:schemaLocation", "http://www.sdmx.org/resources/sdmxml/schemas/v2_1/message ../../schemas/SDMXMessage.xsd");

            headerWriter.writeDefaultHeader(writer);

            writer.writeStartElement(XmlConstants.MESSAGE + XmlConstants.MES_STRUCTURES);

            writerList.forEach(xmlWriter -> {
                try {
                    Set<? extends MaintainableArtefact> maintainableArtefacts = xmlWriter.extractArtefacts(artefacts);
                    if (!maintainableArtefacts.isEmpty()) {
                        writer.writeStartElement(XmlConstants.STR + xmlWriter.getNamePlural());
                        maintainableArtefacts.forEach(artefact -> {
                            try {
                                if (artefact.getVersion().isStable()) {
                                    xmlWriter.write(artefact, writer);
                                }
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
        writer.writeNamespace("mes", XmlConstants.SCHEMAS_V_2_1_MESSAGE);
        writer.writeNamespace("str", XmlConstants.SCHEMAS_V_2_1_STRUCTURE);
        writer.writeNamespace("com", XmlConstants.SCHEMAS_V_2_1_COMMON);
        writer.writeNamespace("xsi", XmlConstants.XMLSCHEMA_INSTANCE);
        writer.writeNamespace("xml", XmlConstants.XML_1998_NAMESPACE);
    }
}
