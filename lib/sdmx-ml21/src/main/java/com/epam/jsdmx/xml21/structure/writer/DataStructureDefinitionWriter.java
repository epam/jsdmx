package com.epam.jsdmx.xml21.structure.writer;

import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinition;
import com.epam.jsdmx.serializer.common.TimeDimensionLocalRepresentationAdapter;
import com.epam.jsdmx.serializer.sdmx21.DataStructure30To21ComponentAdapter;

import org.apache.commons.collections4.CollectionUtils;

public class DataStructureDefinitionWriter extends XmlWriter<DataStructureDefinition> {

    private final AttributeListWriter attributeListWriter;
    private final DimensionListWriter dimensionListWriter;
    private final GroupDimensionListWriter groupDimensionListWriter;
    private final MeasureListWriter measureListWriter;
    private final DataStructure30To21ComponentAdapter dsdComponentAdapter;
    private final TimeDimensionLocalRepresentationAdapter dsdLocalRepresentationAdapter;

    public DataStructureDefinitionWriter(NameableWriter nameableWriter,
                                         AnnotableWriter annotableWriter,
                                         CommonAttributesWriter commonAttributesWriter,
                                         AttributeListWriter attributeListWriter,
                                         DimensionListWriter dimensionListWriter,
                                         MeasureListWriter measureListWriter,
                                         GroupDimensionListWriter groupDimensionListWriter,
                                         DataStructure30To21ComponentAdapter dsdComponentAdapter,
                                         TimeDimensionLocalRepresentationAdapter dsdLocalRepresentationAdapter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter);
        this.attributeListWriter = attributeListWriter;
        this.dimensionListWriter = dimensionListWriter;
        this.measureListWriter = measureListWriter;
        this.groupDimensionListWriter = groupDimensionListWriter;
        this.dsdComponentAdapter = dsdComponentAdapter;
        this.dsdLocalRepresentationAdapter = dsdLocalRepresentationAdapter;
    }

    @Override
    protected void writeAttributes(DataStructureDefinition dsd, XMLStreamWriter writer) throws XMLStreamException {
        this.commonAttributesWriter.writeAttributes(dsd, writer);
    }

    @Override
    protected void writeCustomAttributeElements(DataStructureDefinition dsd, XMLStreamWriter writer) throws XMLStreamException {
        var adaptedDsd = dsdLocalRepresentationAdapter.adapt(dsd);
        adaptedDsd = dsdComponentAdapter.recompose(adaptedDsd);
        writeDataStructureComponents(adaptedDsd, writer);
    }

    @Override
    protected String getName(DataStructureDefinition unused) {
        return XmlConstants.DATA_STRUCTURE;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.DATA_STRUCTURES;
    }

    @Override
    protected Set<DataStructureDefinition> extractArtefacts(Artefacts artefacts) {
        return artefacts.getDataStructures();
    }

    private void writeDataStructureComponents(DataStructureDefinition dsd, XMLStreamWriter writer) throws XMLStreamException {
        if (CollectionUtils.isNotEmpty(dsd.getComponents())) {
            writer.writeStartElement(XmlConstants.STR + XmlConstants.DATA_STRUCTURE_COMPONENTS);
            dimensionListWriter.writeDimensionList(dsd.getDimensionDescriptor(), writer);
            groupDimensionListWriter.writeGroupDimensionList(dsd.getGroupDimensionDescriptors(), writer);
            attributeListWriter.writeAttributeList(dsd.getAttributeDescriptor(), writer);
            measureListWriter.writeMeasureList(dsd.getMeasureDescriptor(), writer);
            writer.writeEndElement();
        }
    }
}