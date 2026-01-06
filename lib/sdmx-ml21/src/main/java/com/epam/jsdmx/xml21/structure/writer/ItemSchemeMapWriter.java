package com.epam.jsdmx.xml21.structure.writer;

import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ItemMap;

import org.apache.commons.collections4.CollectionUtils;

public interface ItemSchemeMapWriter {

    default void writeItems(XMLStreamWriter writer, List<ItemMap> itemMaps, AnnotableWriter annotableWriter) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(itemMaps)) {
            for (ItemMap itemMap : itemMaps) {
                writer.writeStartElement(XmlConstants.STR + XmlConstants.ITEM_MAP);

                writer.writeAttribute(XmlConstants.VALID_TO, XmlWriterUtils.instantToDateString(itemMap.getValidTo()));
                writer.writeAttribute(XmlConstants.VALID_FROM, XmlWriterUtils.instantToDateString(itemMap.getValidFrom()));
                annotableWriter.write(itemMap, writer);

                String source = itemMap.getSource();
                writeSource(writer, source, XmlConstants.STR + XmlConstants.SOURCE_VALUE);

                String target = itemMap.getTarget();
                writeTarget(writer, target, XmlConstants.STR + XmlConstants.TARGET_VALUE);

                writer.writeEndElement();
            }
        }
    }

    default void writeTarget(XMLStreamWriter writer, String target, String name) throws XMLStreamException {
        writer.writeStartElement(name);
        XmlWriterUtils.writeCharacters(target, writer);
        writer.writeEndElement();
    }

    default void writeSource(XMLStreamWriter writer, String source, String name) throws XMLStreamException {
        writer.writeStartElement(name);
        XmlWriterUtils.writeCharacters(source, writer);
        writer.writeEndElement();
    }

}
