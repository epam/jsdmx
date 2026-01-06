package com.epam.jsdmx.xml30.structure.writer;

import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.GroupDimensionDescriptor;

import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

@AllArgsConstructor
public class GroupDimensionListWriter {

    private final AnnotableWriter annotableWriter;

    public void writeGroupDimensionList(List<GroupDimensionDescriptor> groupDimensionDescriptor, XMLStreamWriter writer) throws XMLStreamException {
        if (CollectionUtils.isEmpty(groupDimensionDescriptor)) {
            return;
        }

        for (GroupDimensionDescriptor groupDimension : groupDimensionDescriptor) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.GROUP);
            XmlWriterUtils.writeIdUriAttributes(writer, groupDimension.getId(), groupDimension.getUri());

            this.annotableWriter.write(groupDimension, writer);
            List<String> groupDimensions = groupDimension.getDimensions();
            if (CollectionUtils.isNotEmpty(groupDimensions)) {
                for (String groupDimensionName : groupDimensions) {
                    writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.GROUP_DIMENSION);
                    writeDimensionReference(writer, groupDimensionName);
                    writer.writeEndElement();
                }
            }
            writer.writeEndElement();
        }
    }

    public void writeDimensionReference(XMLStreamWriter writer, String groupDimensionName) throws XMLStreamException {
        writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.DIMENSION_REFERENCE);
        XmlWriterUtils.writeCharacters(groupDimensionName, writer);
        writer.writeEndElement();
    }
}