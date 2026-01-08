package com.epam.jsdmx.xml21.structure.writer;

import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Dataflow;

public class DataflowWriter extends XmlWriter<Dataflow> {

    private final ReferenceWriter referenceWriter;

    public DataflowWriter(NameableWriter nameableWriter,
                          AnnotableWriter annotableWriter,
                          CommonAttributesWriter commonAttributesWriter,
                          ReferenceWriter referenceWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter);
        this.referenceWriter = referenceWriter;
    }

    @Override
    protected void writeAttributes(Dataflow dataflow, XMLStreamWriter writer) throws XMLStreamException {
        this.commonAttributesWriter.writeAttributes(dataflow, writer);
    }

    @Override
    protected void writeCustomAttributeElements(Dataflow dataflow, XMLStreamWriter writer) throws XMLStreamException {
        ArtefactReference structure = dataflow.getStructure();
        if (structure != null) {
            writer.writeStartElement(XmlConstants.STR + XmlConstants.STRUCTURE_UPPER);
            referenceWriter.writeDataStructureReference(writer, structure);
            writer.writeEndElement();
        }
    }

    @Override
    protected String getName(Dataflow unused) {
        return XmlConstants.DATAFLOW;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.DATAFLOWS;
    }

    @Override
    protected Set<Dataflow> extractArtefacts(Artefacts artefacts) {
        return artefacts.getDataflows();
    }
}