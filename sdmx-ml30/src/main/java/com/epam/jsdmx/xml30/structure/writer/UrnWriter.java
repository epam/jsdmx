package com.epam.jsdmx.xml30.structure.writer;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;

public class UrnWriter {

    private final ReferenceAdapter referenceAdapter;

    public UrnWriter(ReferenceAdapter referenceAdapter) {
        this.referenceAdapter = referenceAdapter;
    }

    public void writeUrnCharacters(ArtefactReference ref, XMLStreamWriter writer) throws XMLStreamException {
        if (ref != null) {
            String urn = referenceAdapter.toAdaptedUrn(ref);
            XmlWriterUtils.writeCharacters(urn, writer);
        }
    }

    public void writeUrnCharacters(String ref, XMLStreamWriter writer) throws XMLStreamException {
        if (ref != null) {
            String urn = referenceAdapter.adaptUrn(ref);
            XmlWriterUtils.writeCharacters(urn, writer);
        }
    }

    public void writeUrn(String urn, XMLStreamWriter writer) throws XMLStreamException {
        if (urn != null) {
            XmlWriterUtils.writeUrn(referenceAdapter.adaptUrn(urn), writer);
        }
    }

    public String adaptUrn(String urn) {
        return referenceAdapter.adaptUrn(urn);
    }

    public String adaptType(String type) {
        return referenceAdapter.adaptType(type);
    }
}
