package com.epam.jsdmx.xml21.structure.writer;

import static com.epam.jsdmx.xml21.structure.writer.XmlWriterUtils.writeInternationalString;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.NameableArtefact;

public class NameableWriter {

    public void write(NameableArtefact artefact, XMLStreamWriter writer) throws XMLStreamException {
        InternationalString name = artefact.getName();
        writeInternationalString(name, writer, XmlConstants.COMMON + XmlConstants.COM_NAME);
        InternationalString description = artefact.getDescription();
        writeInternationalString(description, writer, XmlConstants.COMMON + XmlConstants.COM_DESCRIPTION);
    }
}
