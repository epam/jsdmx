package com.epam.jsdmx.xml30.structure.writer;

import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefact;
import com.epam.jsdmx.infomodel.sdmx30.StructureClass;

public abstract class XmlWriter<T extends MaintainableArtefact> {

    protected NameableWriter nameableWriter;
    protected AnnotableWriter annotableWriter;
    protected CommonAttributesWriter commonAttributesWriter;
    protected LinksWriter linksWriter;


    protected XmlWriter(NameableWriter nameableWriter,
                        AnnotableWriter annotableWriter,
                        CommonAttributesWriter commonAttributesWriter,
                        LinksWriter linksWriter) {
        this.nameableWriter = nameableWriter;
        this.annotableWriter = annotableWriter;
        this.commonAttributesWriter = commonAttributesWriter;
        this.linksWriter = linksWriter;
    }

    protected abstract void writeAttributes(T artefact, XMLStreamWriter writer) throws XMLStreamException;

    protected abstract void writeCustomAttributeElements(T artefact, XMLStreamWriter writer) throws XMLStreamException;

    protected abstract String getName();

    protected abstract String getNamePlural();


    protected void writeCommon(T artefact, XMLStreamWriter writer) throws XMLStreamException {
        annotableWriter.write(artefact, writer);
        linksWriter.write(artefact.getLinks(), writer);
        nameableWriter.write(artefact, writer);
    }

    protected abstract Set<T> extractArtefacts(Artefacts artefacts);

    public void write(MaintainableArtefact maintainableArtefact, XMLStreamWriter writer) throws XMLStreamException {
        T artefact = (T) maintainableArtefact;
        writer.writeStartElement(XmlConstants.STRUCTURE + getName());
        writeAttributes(artefact, writer);
        writeCommon(artefact, writer);
        writeCustomAttributeElements(artefact, writer);
        writer.writeEndElement();
        cleanData();
    }

    public Optional<StructureClass> getWritableArtefactStructureClass() {
        // if there is a need to find writer by its maintainable artefact type
        return Optional.empty();
    }

    private void cleanData() {
        //individual for each artefact
    }
}
