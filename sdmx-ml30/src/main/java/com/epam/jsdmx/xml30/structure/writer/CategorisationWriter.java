package com.epam.jsdmx.xml30.structure.writer;

import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Categorisation;

public class CategorisationWriter extends XmlWriter<Categorisation> {

    private final UrnWriter urnWriter;

    public CategorisationWriter(NameableWriter nameableWriter,
                                AnnotableWriter annotableWriter,
                                CommonAttributesWriter commonAttributesWriter,
                                LinksWriter linksWriter,
                                UrnWriter urnWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
        this.urnWriter = urnWriter;
    }

    @Override
    protected void writeAttributes(Categorisation categorisation, XMLStreamWriter writer) throws XMLStreamException {
        this.commonAttributesWriter.writeAttributes(categorisation, writer);
    }

    @Override
    protected void writeCustomAttributeElements(Categorisation categorisation, XMLStreamWriter writer) throws XMLStreamException {

        ArtefactReference categorizedArtefact = categorisation.getCategorizedArtefact();
        ArtefactReference categorizedBy = categorisation.getCategorizedBy();
        if (categorizedArtefact != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.SOURCE);
            urnWriter.writeUrnCharacters(categorizedArtefact, writer);
            writer.writeEndElement();
        }

        if (categorizedBy != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + XmlConstants.TARGET);
            urnWriter.writeUrnCharacters(categorizedBy, writer);
            writer.writeEndElement();
        }
    }

    @Override
    protected String getName() {
        return XmlConstants.CATEGORISATION;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.CATEGORISATIONS;
    }

    @Override
    protected Set<Categorisation> extractArtefacts(Artefacts artefacts) {
        return artefacts.getCategorisations();
    }
}
