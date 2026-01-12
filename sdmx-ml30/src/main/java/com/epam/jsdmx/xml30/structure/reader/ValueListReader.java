package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEndingTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.moveToNextTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.setCommonAttributes;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.ValueItem;
import com.epam.jsdmx.infomodel.sdmx30.ValueItemImpl;
import com.epam.jsdmx.infomodel.sdmx30.ValueListImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import org.apache.commons.lang3.StringUtils;

public class ValueListReader extends XmlReader<ValueListImpl> {

    private List<ValueItem> valueItems;

    public ValueListReader(AnnotableReader annotableReader,
                           NameableReader nameableReader) {
        super(annotableReader, nameableReader);
    }

    @Override
    protected ValueListImpl read(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        ValueListImpl valueList = super.read(reader);
        valueList.setItems(new ArrayList<>(valueItems));
        return valueList;
    }

    @Override
    protected void read(XMLStreamReader reader, ValueListImpl maintainableArtefact) throws URISyntaxException, XMLStreamException {
        var valueItem = new ValueItemImpl();
        String localName = reader.getLocalName();
        valueItems = new ArrayList<>();
        if (!XmlConstants.VALUE_ITEM.equals(localName)) {
            throw new IllegalArgumentException("ValueList " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
        }

        readValueItem(reader, valueItem);
    }

    @Override
    protected ValueListImpl createMaintainableArtefact() {
        return new ValueListImpl();
    }

    private void readValueItem(XMLStreamReader reader, ValueItemImpl valueItem) throws XMLStreamException, URISyntaxException {
        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.ID))
            .ifPresent(valueItem::setId);

        Map<String, String> names = new HashMap<>();
        Map<String, String> descriptions = new HashMap<>();
        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.VALUE_ITEM)) {
            String localName = reader.getLocalName();
            switch (localName) {
                case XmlConstants.COM_ANNOTATIONS:
                    annotableReader.setAnnotations(reader, valueItem);
                    break;
                case XmlConstants.COM_NAME:
                    nameableReader.setNameable(reader, names);
                    break;
                case XmlConstants.COM_DESCRIPTION:
                    nameableReader.setNameable(reader, descriptions);
                    break;
                default:
                    throw new IllegalArgumentException("ValueItem " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
            }
            moveToNextTag(reader);
        }
        valueItem.setName(new InternationalString(names));
        valueItem.setDescription(new InternationalString(descriptions));
        valueItems.add(valueItem);
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, ValueListImpl maintainableArtefact) throws XMLStreamException {
        setCommonAttributes(reader, maintainableArtefact);
    }

    @Override
    protected String getName() {
        return XmlConstants.VALUE_LIST;
    }

    @Override
    protected String getNames() {
        return XmlConstants.VALUE_LISTS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<ValueListImpl> artefacts) {
        artefact.getValueLists().addAll(artefacts);
    }

    @Override
    protected void clean() {
        valueItems.clear();
    }
}
