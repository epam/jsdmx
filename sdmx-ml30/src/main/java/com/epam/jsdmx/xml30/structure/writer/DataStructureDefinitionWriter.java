package com.epam.jsdmx.xml30.structure.writer;

import static com.epam.jsdmx.xml30.structure.writer.XmlWriterUtils.writeCharacters;

import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinition;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefact;
import com.epam.jsdmx.serializer.common.TimeDimensionLocalRepresentationAdapter;

import org.apache.commons.collections4.CollectionUtils;

public class DataStructureDefinitionWriter extends XmlWriter<DataStructureDefinition> {

    private final AttributeListWriter attributeListWriter;
    private final DimensionListWriter dimensionListWriter;
    private final MeasureListWriter measureListWriter;
    private final GroupDimensionListWriter groupDimensionListWriter;
    private final TimeDimensionLocalRepresentationAdapter timeDimensionLocalRepresentationAdapter;

    public DataStructureDefinitionWriter(NameableWriter nameableWriter,
                                         AnnotableWriter annotableWriter,
                                         CommonAttributesWriter commonAttributesWriter,
                                         LinksWriter linksWriter,
                                         AttributeListWriter attributeListWriter,
                                         DimensionListWriter dimensionListWriter,
                                         MeasureListWriter measureListWriter,
                                         GroupDimensionListWriter groupDimensionListWriter,
                                         TimeDimensionLocalRepresentationAdapter timeDimensionLocalRepresentationAdapter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
        this.attributeListWriter = attributeListWriter;
        this.dimensionListWriter = dimensionListWriter;
        this.measureListWriter = measureListWriter;
        this.groupDimensionListWriter = groupDimensionListWriter;
        this.timeDimensionLocalRepresentationAdapter = timeDimensionLocalRepresentationAdapter;
    }

    @Override
    public void write(MaintainableArtefact maintainableArtefact, XMLStreamWriter writer) throws XMLStreamException {
        DataStructureDefinition artefact = (DataStructureDefinition) maintainableArtefact;
        artefact = timeDimensionLocalRepresentationAdapter.adapt(artefact);
        super.write(artefact, writer);
    }

    @Override
    protected void writeAttributes(DataStructureDefinition dsd, XMLStreamWriter writer) throws XMLStreamException {
        this.commonAttributesWriter.writeAttributes(dsd, writer);
    }

    @Override
    protected void writeCustomAttributeElements(DataStructureDefinition dsd, XMLStreamWriter writer) throws XMLStreamException {
        writeDataStructureComponents(dsd, writer);
        ArtefactReference metadataStructure = dsd.getMetadataStructure();
        if (metadataStructure != null) {
            writeMetadata(metadataStructure, writer);
        }
    }

    @Override
    protected String getName() {
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
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.DATA_STRUCTURE_COMPONENTS);
            dimensionListWriter.writeDimensionList(dsd.getDimensionDescriptor(), writer);
            groupDimensionListWriter.writeGroupDimensionList(dsd.getGroupDimensionDescriptors(), writer);
            attributeListWriter.writeAttributeList(dsd.getAttributeDescriptor(), writer);
            measureListWriter.writeMeasureList(dsd.getMeasureDescriptor(), writer);
            writer.writeEndElement();
        }
    }

    private void writeMetadata(ArtefactReference metadataStructure, XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.METADATA);
        if (metadataStructure != null) {
            writeCharacters(metadataStructure.getUrn(), writer);
        }
        writer.writeEndElement();
    }
}