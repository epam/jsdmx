package com.epam.jsdmx.xml30.structure.reader;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ItemMap;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.OrganisationSchemeMapImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

public class OrganisationSchemeMapReader extends XmlReader<OrganisationSchemeMapImpl> implements ItemSchemeMapReader {

    private final List<ItemMap> itemMaps = new ArrayList<>();

    public OrganisationSchemeMapReader(AnnotableReader annotableReader,
                                       NameableReader nameableReader) {
        super(annotableReader, nameableReader);
    }

    @Override
    protected OrganisationSchemeMapImpl createMaintainableArtefact() {
        return new OrganisationSchemeMapImpl();
    }

    @Override
    protected OrganisationSchemeMapImpl read(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        OrganisationSchemeMapImpl organisationSchemeMap = super.read(reader);
        organisationSchemeMap.setItemMaps(new ArrayList<>(itemMaps));
        return organisationSchemeMap;
    }

    @Override
    protected void read(XMLStreamReader reader, OrganisationSchemeMapImpl organisationSchemeMap) throws URISyntaxException, XMLStreamException {
        String localName = reader.getLocalName();
        switch (localName) {
            case XmlConstants.SOURCE:
                Optional.ofNullable(reader.getElementText())
                    .map(MaintainableArtefactReference::new)
                    .ifPresent(organisationSchemeMap::setSource);
                break;
            case XmlConstants.TARGET:
                Optional.ofNullable(reader.getElementText())
                    .map(MaintainableArtefactReference::new)
                    .ifPresent(organisationSchemeMap::setTarget);
                break;
            case XmlConstants.ITEM_MAP:
                itemMaps.add(getItemMap(reader, annotableReader));
                break;
            default:
                throw new IllegalArgumentException("CategorySchemeMap " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
        }
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, OrganisationSchemeMapImpl maintainableArtefact) throws XMLStreamException {
        XmlReaderUtils.setCommonAttributes(reader, maintainableArtefact);
    }

    @Override
    protected String getName() {
        return XmlConstants.ORGANISATION_SCHEME_MAP;
    }

    @Override
    protected String getNames() {
        return XmlConstants.ORGANISATION_SCHEME_MAPS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<OrganisationSchemeMapImpl> artefacts) {
        artefact.getOrganisationSchemeMaps().addAll(artefacts);
    }


    @Override
    protected void clean() {
        itemMaps.clear();
    }
}
