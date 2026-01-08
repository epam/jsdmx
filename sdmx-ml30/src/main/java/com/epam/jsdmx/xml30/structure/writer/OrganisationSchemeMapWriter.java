package com.epam.jsdmx.xml30.structure.writer;

import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ItemMap;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationSchemeMap;

public class OrganisationSchemeMapWriter extends XmlWriter<OrganisationSchemeMap> {

    private final ItemSchemeMapWriter itemSchemeMapWriter;
    private final UrnWriter urnWriter;

    public OrganisationSchemeMapWriter(NameableWriter nameableWriter,
                                       AnnotableWriter annotableWriter,
                                       CommonAttributesWriter commonAttributesWriter,
                                       LinksWriter linksWriter,
                                       ItemSchemeMapWriter itemSchemeMapWriter,
                                       UrnWriter urnWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
        this.itemSchemeMapWriter = itemSchemeMapWriter;
        this.urnWriter = urnWriter;
    }

    @Override
    protected void writeAttributes(OrganisationSchemeMap artefact, XMLStreamWriter writer) throws XMLStreamException {
        this.commonAttributesWriter.writeAttributes(artefact, writer);
    }

    @Override
    protected void writeCustomAttributeElements(OrganisationSchemeMap organisationSchemeMap, XMLStreamWriter writer) throws XMLStreamException {
        ArtefactReference source = organisationSchemeMap.getSource();
        writeArtefactReference(source, writer, XmlConstants.SOURCE);

        ArtefactReference target = organisationSchemeMap.getTarget();
        writeArtefactReference(target, writer, XmlConstants.TARGET);

        List<ItemMap> itemMaps = organisationSchemeMap.getItemMaps();
        itemSchemeMapWriter.writeItems(writer, itemMaps, annotableWriter);
    }

    private void writeArtefactReference(ArtefactReference artefactReference,
                                        XMLStreamWriter writer,
                                        String refName) throws XMLStreamException {
        if (artefactReference != null) {
            writer.writeStartElement(XmlConstants.STRUCTURE + refName);
            urnWriter.writeUrnCharacters(artefactReference, writer);
            writer.writeEndElement();
        }
    }

    @Override
    protected String getName() {
        return XmlConstants.ORGANISATION_SCHEME_MAP;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.ORGANISATION_SCHEME_MAPS;
    }

    @Override
    protected Set<OrganisationSchemeMap> extractArtefacts(Artefacts artefacts) {
        return artefacts.getOrganisationSchemeMaps();
    }
}
