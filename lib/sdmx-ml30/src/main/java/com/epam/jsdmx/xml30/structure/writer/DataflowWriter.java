package com.epam.jsdmx.xml30.structure.writer;

import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Dataflow;

public class DataflowWriter extends XmlWriter<Dataflow> {

    public DataflowWriter(NameableWriter nameableWriter,
                          AnnotableWriter annotableWriter,
                          CommonAttributesWriter commonAttributesWriter,
                          LinksWriter linksWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
    }

    @Override
    protected void writeAttributes(Dataflow dataflow, XMLStreamWriter writer) throws XMLStreamException {
        this.commonAttributesWriter.writeAttributes(dataflow, writer);
    }

    @Override
    protected void writeCustomAttributeElements(Dataflow dataflow, XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.STRUCTURE_UPPER);
        ArtefactReference structure = dataflow.getStructure();
        if (structure != null) {
            XmlWriterUtils.writeCharacters(structure.getUrn(), writer);
        }
        writer.writeEndElement();
    }

    @Override
    protected String getName() {
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