package com.epam.jsdmx.xml30.structure.writer;

import static com.epam.jsdmx.xml30.structure.writer.XmlWriterUtils.writeInternationalString;

import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.ValueItem;
import com.epam.jsdmx.infomodel.sdmx30.ValueList;

import org.apache.commons.collections.CollectionUtils;

public class ValueListWriter extends XmlWriter<ValueList> {

    public ValueListWriter(NameableWriter nameableWriter,
                           AnnotableWriter annotableWriter,
                           CommonAttributesWriter commonAttributesWriter,
                           LinksWriter linksWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
    }

    @Override
    protected void writeAttributes(ValueList artefact, XMLStreamWriter writer) throws XMLStreamException {
        commonAttributesWriter.writeAttributes(artefact, writer);
    }

    @Override
    protected void writeCustomAttributeElements(ValueList valueList, XMLStreamWriter writer) throws XMLStreamException {
        List<ValueItem> items = valueList.getItems();
        if (CollectionUtils.isNotEmpty(items)) {
            for (ValueItem valueItem : items) {
                if (valueItem != null) {
                    writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.VALUE_ITEM);
                    writer.writeAttribute(XmlConstants.ID, valueItem.getId());
                    annotableWriter.write(valueItem, writer);
                    InternationalString name = valueItem.getName();
                    writeInternationalString(name, writer, XmlConstants.COMMON + XmlConstants.COM_NAME);
                    InternationalString description = valueItem.getDescription();
                    writeInternationalString(description, writer, XmlConstants.COMMON + XmlConstants.COM_DESCRIPTION);
                    writer.writeEndElement();
                }
            }
        }
    }

    @Override
    protected String getName() {
        return XmlConstants.VALUE_LIST;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.VALUE_LISTS;
    }

    @Override
    protected Set<ValueList> extractArtefacts(Artefacts artefacts) {
        return artefacts.getValueLists();
    }
}
