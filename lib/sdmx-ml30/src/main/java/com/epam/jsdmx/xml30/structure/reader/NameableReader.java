package com.epam.jsdmx.xml30.structure.reader;

import java.util.Map;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

public class NameableReader {

    public void setNameable(XMLStreamReader reader, Map<String, String> nameableMap) throws XMLStreamException {
        String locale = reader.getAttributeValue(XmlConstants.XML_1998_NAMESPACE, XmlConstants.LANG);
        String text = reader.getElementText();
        if (locale != null && text != null) {
            nameableMap.put(locale, text);
        }
    }
}
