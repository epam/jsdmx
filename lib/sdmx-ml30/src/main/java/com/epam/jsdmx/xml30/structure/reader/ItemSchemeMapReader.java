package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEndingTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.moveToNextTag;

import java.net.URISyntaxException;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.ItemMap;
import com.epam.jsdmx.infomodel.sdmx30.ItemMapImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

public interface ItemSchemeMapReader {

    default ItemMap getItemMap(XMLStreamReader reader, AnnotableReader annotableReader) throws XMLStreamException, URISyntaxException {
        var itemMap = new ItemMapImpl();
        itemMap.setValidFrom(XmlReaderUtils.getDate(reader, XmlConstants.VALID_FROM));
        itemMap.setValidTo(XmlReaderUtils.getDate(reader, XmlConstants.VALID_TO));

        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.ITEM_MAP)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, itemMap);
                    break;
                case XmlConstants.SOURCE_VALUE:
                    String source = reader.getElementText();
                    if (source != null) {
                        itemMap.setSource(source);
                    }
                    break;
                case XmlConstants.TARGET_VALUE:
                    String target = reader.getElementText();
                    if (target != null) {
                        itemMap.setTarget(target);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("ItemMap " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
        return itemMap;
    }
}
