package com.epam.jsdmx.xml30.structure.writer;


import static com.epam.jsdmx.xml30.structure.writer.XmlWriterUtils.writeCharacters;

import java.net.URI;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Contact;

import org.apache.commons.collections.CollectionUtils;

public class ContactWriter {

    public void writeContacts(List<Contact> contacts, XMLStreamWriter writer) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(contacts)) {
            for (Contact contact : contacts) {
                writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.CONTACT);

                writeElement(writer, contact.getName(), XmlConstants.COMMON + XmlConstants.NAME);
                writeElement(writer, contact.getOrganizationUnit(), XmlConstants.STRUCTURE + XmlConstants.DEPARTMENT);
                XmlWriterUtils.writeInternationalString(contact.getResponsibility(), writer, XmlConstants.STRUCTURE + XmlConstants.ROLE);

                writeContactData(writer, contact.getTelephone(), XmlConstants.TELEPHONE);
                writeContactData(writer, contact.getFax(), XmlConstants.FAX);
                writeContactData(writer, contact.getX400(), XmlConstants.X_400);
                URI uri = contact.getUri();
                if (uri != null) {
                    writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.URI);
                    writeCharacters(uri.toString(), writer);
                    writer.writeEndElement();
                }
                writeContactData(writer, contact.getEmail(), XmlConstants.EMAIL);

                writer.writeEndElement();
            }
        }
    }

    private void writeContactData(XMLStreamWriter writer, String data, String name) throws XMLStreamException {
        if (data != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + name);
            writer.writeCharacters(data);
            writer.writeEndElement();
        }
    }

    private void writeElement(XMLStreamWriter writer, String contactElement, String nameElement) throws XMLStreamException {
        if (contactElement != null) {
            writer.writeStartElement(nameElement);
            writer.writeAttribute(XmlConstants.XML_LANG, "en");
            writer.writeCharacters(contactElement);
            writer.writeEndElement();
        }
    }
}
