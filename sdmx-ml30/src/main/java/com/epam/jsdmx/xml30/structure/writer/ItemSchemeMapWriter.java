package com.epam.jsdmx.xml30.structure.writer;

import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.ItemMap;

import org.apache.commons.collections.CollectionUtils;

public class ItemSchemeMapWriter {

    private final UrnWriter urnWriter;

    public ItemSchemeMapWriter(UrnWriter urnWriter) {
        this.urnWriter = urnWriter;
    }

    public void writeItems(XMLStreamWriter writer, List<ItemMap> itemMaps, AnnotableWriter annotableWriter) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(itemMaps)) {
            for (ItemMap itemMap : itemMaps) {
                writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.ITEM_MAP);

                writer.writeAttribute(XmlConstants.VALID_TO, XmlWriterUtils.instantToDateString(itemMap.getValidTo()));
                writer.writeAttribute(XmlConstants.VALID_FROM, XmlWriterUtils.instantToDateString(itemMap.getValidFrom()));
                annotableWriter.write(itemMap, writer);

                String source = itemMap.getSource();
                writeSource(writer, source, XmlConstants.STRUCTURE + XmlConstants.SOURCE_VALUE);

                String target = itemMap.getTarget();
                writeTarget(writer, target, XmlConstants.STRUCTURE + XmlConstants.TARGET_VALUE);

                writer.writeEndElement();
            }
        }
    }

    public void writeTarget(XMLStreamWriter writer, String target, String name) throws XMLStreamException {
        writer.writeStartElement(name);
        urnWriter.writeUrnCharacters(target, writer);
        writer.writeEndElement();
    }

    public void writeTarget(XMLStreamWriter writer, ArtefactReference target, String name) throws XMLStreamException {
        writer.writeStartElement(name);
        urnWriter.writeUrnCharacters(target, writer);
        writer.writeEndElement();
    }

    public void writeSource(XMLStreamWriter writer, String source, String name) throws XMLStreamException {
        writer.writeStartElement(name);
        urnWriter.writeUrnCharacters(source, writer);
        writer.writeEndElement();
    }

    public void writeSource(XMLStreamWriter writer, ArtefactReference source, String name) throws XMLStreamException {
        writer.writeStartElement(name);
        urnWriter.writeUrnCharacters(source, writer);
        writer.writeEndElement();
    }

}
