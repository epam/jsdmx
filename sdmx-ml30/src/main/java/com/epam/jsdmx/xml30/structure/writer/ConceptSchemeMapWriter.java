package com.epam.jsdmx.xml30.structure.writer;

import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ConceptSchemeMap;

public class ConceptSchemeMapWriter extends XmlWriter<ConceptSchemeMap> {

    private final ItemSchemeMapWriter itemSchemeMapWriter;

    public ConceptSchemeMapWriter(NameableWriter nameableWriter,
                                  AnnotableWriter annotableWriter,
                                  CommonAttributesWriter commonAttributesWriter,
                                  LinksWriter linksWriter,
                                  ItemSchemeMapWriter itemSchemeMapWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
        this.itemSchemeMapWriter = itemSchemeMapWriter;
    }

    @Override
    protected void writeAttributes(ConceptSchemeMap conceptSchemeMap, XMLStreamWriter writer) throws XMLStreamException {
        commonAttributesWriter.writeAttributes(conceptSchemeMap, writer);
    }

    @Override
    protected void writeCustomAttributeElements(ConceptSchemeMap conceptSchemeMapc, XMLStreamWriter writer) throws XMLStreamException {
        itemSchemeMapWriter.writeSource(writer, conceptSchemeMapc.getSource().getUrn(), XmlConstants.STRUCTURE + XmlConstants.SOURCE);
        itemSchemeMapWriter.writeTarget(writer, conceptSchemeMapc.getTarget().getUrn(), XmlConstants.STRUCTURE + XmlConstants.TARGET);
        itemSchemeMapWriter.writeItems(writer, conceptSchemeMapc.getItemMaps(), annotableWriter);
    }

    @Override
    protected String getName() {
        return XmlConstants.CONCEPT_SCHEME_MAP;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.CONCEPT_SCHEME_MAPS;
    }

    @Override
    protected Set<ConceptSchemeMap> extractArtefacts(Artefacts artefacts) {
        return artefacts.getConceptSchemeMaps();
    }
}
