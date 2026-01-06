package com.epam.jsdmx.xml21.structure.writer;

import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Categorisation;


public class CategorisationWriter extends XmlWriter<Categorisation> {

    private final ReferenceWriter referenceWriter;

    public CategorisationWriter(NameableWriter nameableWriter,
                                AnnotableWriter annotableWriter,
                                CommonAttributesWriter commonAttributesWriter,
                                ReferenceWriter referenceWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter);
        this.referenceWriter = referenceWriter;
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
            writer.writeStartElement(XmlConstants.STR + XmlConstants.SOURCE);
            referenceWriter.writeObjectReference(writer, categorizedArtefact);
            writer.writeEndElement();
        }

        if (categorizedBy != null) {
            writer.writeStartElement(XmlConstants.STR + XmlConstants.TARGET);
            referenceWriter.writeCategoryReference(writer, categorizedBy);
            writer.writeEndElement();
        }
    }

    @Override
    protected String getName(Categorisation unused) {
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
