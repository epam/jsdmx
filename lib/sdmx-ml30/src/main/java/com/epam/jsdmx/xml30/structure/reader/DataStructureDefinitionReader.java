package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.isNotEndingTag;
import static com.epam.jsdmx.xml30.structure.reader.XmlReaderUtils.moveToNextTag;

import java.net.URISyntaxException;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.AttributeDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinitionImpl;
import com.epam.jsdmx.infomodel.sdmx30.DimensionDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.MeasureDescriptorImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

public class DataStructureDefinitionReader extends XmlReader<DataStructureDefinitionImpl> {

    private final AttributeListReader attributeListReader;
    private final DimensionListReader dimensionListReader;
    private final MeasureListReader measureListReader;

    public DataStructureDefinitionReader(AnnotableReader annotableReader,
                                         NameableReader nameableReader,
                                         AttributeListReader attributeListReader,
                                         DimensionListReader dimensionListReader,
                                         MeasureListReader measureListReader) {
        super(annotableReader, nameableReader);
        this.attributeListReader = attributeListReader;
        this.dimensionListReader = dimensionListReader;
        this.measureListReader = measureListReader;
    }

    @Override
    protected DataStructureDefinitionImpl createMaintainableArtefact() {
        return new DataStructureDefinitionImpl();
    }

    @Override
    protected void read(XMLStreamReader reader, DataStructureDefinitionImpl dsd) throws URISyntaxException, XMLStreamException {
        String localName = reader.getLocalName();
        switch (localName) {
            case XmlConstants.METADATA:
                Optional.ofNullable(reader.getElementText())
                    .filter(XmlReaderUtils::isNotEmptyOrNullElementText)
                    .map(MaintainableArtefactReference::new)
                    .ifPresent(dsd::setMetadataStructure);
                break;
            case XmlConstants.DATA_STRUCTURE_COMPONENTS:
                readDataStructures(reader, dsd);
                break;
            default:
                throw new IllegalArgumentException("DataStructureDefinition " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
        }
    }

    private void readDataStructures(XMLStreamReader reader, DataStructureDefinitionImpl dsd) throws XMLStreamException, URISyntaxException {
        moveToNextTag(reader);
        while (isNotEndingTag(reader, XmlConstants.DATA_STRUCTURE_COMPONENTS)) {
            String name = reader.getLocalName();
            switch (name) {
                case XmlConstants.ATTRIBUTE_LIST:
                    var attributeDescriptor = new AttributeDescriptorImpl();
                    attributeListReader.read(reader, attributeDescriptor);
                    dsd.setAttributeDescriptor(attributeDescriptor);
                    break;
                case XmlConstants.DIMENSION_LIST:
                    var dimensionDescriptor = new DimensionDescriptorImpl();
                    dimensionListReader.read(reader, dimensionDescriptor);
                    dsd.setDimensionDescriptor(dimensionDescriptor);
                    break;
                case XmlConstants.MEASURE_LIST:
                    var measureDescriptor = new MeasureDescriptorImpl();
                    measureListReader.read(reader, measureDescriptor);
                    dsd.setMeasureDescriptor(measureDescriptor);
                    break;
                case XmlConstants.GROUP:
                    // Skip it
                    while (isNotEndingTag(reader, XmlConstants.GROUP)) {
                        moveToNextTag(reader);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("DataStructureComponents " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + name);
            }
            moveToNextTag(reader);
        }
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, DataStructureDefinitionImpl maintainableArtefact) throws XMLStreamException {
        XmlReaderUtils.setCommonAttributes(reader, maintainableArtefact);
    }

    @Override
    protected String getName() {
        return XmlConstants.DATA_STRUCTURE;
    }

    @Override
    protected String getNames() {
        return XmlConstants.DATA_STRUCTURES;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<DataStructureDefinitionImpl> artefacts) {
        artefact.getDataStructures().addAll(artefacts);
    }
}
