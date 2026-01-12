package com.epam.jsdmx.xml21.structure.writer;

import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ComponentValue;
import com.epam.jsdmx.infomodel.sdmx30.DataKey;
import com.epam.jsdmx.infomodel.sdmx30.DataKeySet;

import org.apache.commons.collections4.CollectionUtils;

public class DataKeySetsWriter {

    private final AnnotableWriter annotableWriter;
    private final MemberSelectionWriter memberSelectionWriter;

    public DataKeySetsWriter(AnnotableWriter annotableWriter, MemberSelectionWriter memberSelectionWriter) {
        this.annotableWriter = annotableWriter;
        this.memberSelectionWriter = memberSelectionWriter;
    }

    public void writeDataKeySets(XMLStreamWriter writer, List<DataKeySet> dataKeySets) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(dataKeySets)) {
            for (DataKeySet dataKeySet : dataKeySets) {
                if (dataKeySet != null) {
                    writer.writeStartElement(XmlConstants.STR + XmlConstants.DATA_KEY_SET);
                    writer.writeAttribute(XmlConstants.IS_INCLUDED, String.valueOf(dataKeySet.isIncluded()));
                    List<DataKey> keys = dataKeySet.getKeys();
                    writeDataKeys(keys, writer);
                    writer.writeEndElement();
                }
            }
        }
    }

    private void writeDataKeys(List<DataKey> keys, XMLStreamWriter writer) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(keys)) {
            for (DataKey dataKey : keys) {
                if (dataKey != null) {
                    writer.writeStartElement(XmlConstants.COMMON + XmlConstants.KEY);
                    writer.writeAttribute(XmlConstants.INCLUDE, String.valueOf(dataKey.isIncluded()));
                    annotableWriter.write(dataKey, writer);
                    List<ComponentValue> keyValues = dataKey.getKeyValues();
                    writeKeyValues(keyValues, writer);
                    writer.writeEndElement();
                }
            }
        }
    }

    private void writeKeyValues(List<ComponentValue> keyValues, XMLStreamWriter writer) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(keyValues)) {
            for (ComponentValue componentValue : keyValues) {
                if (componentValue != null) {
                    writer.writeStartElement(XmlConstants.COMMON + XmlConstants.KEY_VALUE);

                    writer.writeAttribute(XmlConstants.ID, componentValue.getComponentId());
                    writer.writeAttribute(XmlConstants.INCLUDE, String.valueOf(componentValue.isIncluded()));

                    writer.writeStartElement(XmlConstants.COMMON + XmlConstants.VALUE);
                    XmlWriterUtils.writeCharacters(componentValue.getValue(), writer);
                    writer.writeEndElement();

                    writer.writeEndElement();
                }
            }
        }
    }

}
