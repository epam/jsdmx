package com.epam.jsdmx.xml21.structure.writer;

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

    protected XmlWriter(NameableWriter nameableWriter,
                        AnnotableWriter annotableWriter,
                        CommonAttributesWriter commonAttributesWriter) {
        this.nameableWriter = nameableWriter;
        this.annotableWriter = annotableWriter;
        this.commonAttributesWriter = commonAttributesWriter;
    }

    protected abstract void writeAttributes(T artefact, XMLStreamWriter writer) throws XMLStreamException;

    protected abstract void writeCustomAttributeElements(T artefact, XMLStreamWriter writer) throws XMLStreamException;

    protected abstract String getName(T artefact);

    protected abstract String getNamePlural();


    public Optional<StructureClass> getWritableArtefactStructureClass() {
        // if there is a need to find writer by its maintainable artefact type
        return Optional.empty();
    }
    protected void writeCommon(T artefact, XMLStreamWriter writer) throws XMLStreamException {
        annotableWriter.write(artefact, writer);
        nameableWriter.write(artefact, writer);
    }

    protected abstract Set<T> extractArtefacts(Artefacts artefacts);

    void write(MaintainableArtefact maintainableArtefact, XMLStreamWriter writer) throws XMLStreamException {
        T artefact = (T) maintainableArtefact;
        writer.writeStartElement(XmlConstants.STR + getName(artefact));
        writeAttributes(artefact, writer);
        writeCommon(artefact, writer);
        writeCustomAttributeElements(artefact, writer);
        writer.writeEndElement();
        cleanData();
    }

    private void cleanData() {
        //individual for each artefact
    }
}
