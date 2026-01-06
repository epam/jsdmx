package com.epam.jsdmx.xml21.structure.writer;

import java.time.Instant;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.Party;
import com.epam.jsdmx.serializer.sdmx30.common.DefaultHeaderProvider;
import com.epam.jsdmx.serializer.sdmx30.common.Header;

import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

@AllArgsConstructor
public class HeaderWriter {

    DefaultHeaderProvider defaultHeaderProvider;

    public void writeDefaultHeader(XMLStreamWriter writer) throws XMLStreamException {
        Header header = defaultHeaderProvider.provide();
        write(writer, header);
    }

    public void write(XMLStreamWriter writer, Header header) throws XMLStreamException {
        writer.writeStartElement(XmlConstants.MESSAGE + XmlConstants.HEADER);

        String id = header.getId();
        if (id != null) {
            writer.writeStartElement(XmlConstants.MESSAGE + XmlConstants.MES_ID);
            writer.writeCharacters(id);
            writer.writeEndElement();
        }

        boolean test = header.isTest();
        writer.writeStartElement(XmlConstants.MESSAGE + XmlConstants.MES_TEST);
        writer.writeCharacters(String.valueOf(test));
        writer.writeEndElement();

        Instant prepared = header.getPrepared();
        if (prepared != null) {
            writer.writeStartElement(XmlConstants.MESSAGE + XmlConstants.PREPARED);
            writer.writeCharacters(prepared.toString());
            writer.writeEndElement();
        }

        Party sender = header.getSender();
        if (sender != null) {
            String senderId = sender.getId();
            if (senderId != null) {
                writer.writeStartElement(XmlConstants.MESSAGE + XmlConstants.SENDER);
                writer.writeAttribute(XmlConstants.ID, senderId);
                InternationalString name = header.getName();
                XmlWriterUtils.writeInternationalString(name, writer, XmlConstants.COMMON + XmlConstants.COM_NAME);
                writer.writeEndElement();
            }
        }

        List<Party> receivers = header.getReceivers();
        if (CollectionUtils.isNotEmpty(receivers)) {
            for (Party receiver : receivers) {
                writer.writeStartElement(XmlConstants.MESSAGE + XmlConstants.RECEIVER);
                String receiverId = receiver.getId();
                if (receiverId != null) {
                    writer.writeAttribute(XmlConstants.ID, receiverId);
                }
                InternationalString name = header.getName();
                XmlWriterUtils.writeInternationalString(name, writer, XmlConstants.COMMON + XmlConstants.COM_NAME);
                writer.writeEndElement();
            }
        }

        writer.writeEndElement();
    }
}