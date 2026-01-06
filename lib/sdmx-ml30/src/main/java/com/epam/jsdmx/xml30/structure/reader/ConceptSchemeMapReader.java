package com.epam.jsdmx.xml30.structure.reader;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ConceptSchemeMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.ItemMap;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

public class ConceptSchemeMapReader extends XmlReader<ConceptSchemeMapImpl> implements ItemSchemeMapReader {

    private final List<ItemMap> itemMaps = new ArrayList<>();

    public ConceptSchemeMapReader(AnnotableReader annotableReader,
                                  NameableReader nameableReader) {
        super(annotableReader, nameableReader);
    }

    @Override
    protected ConceptSchemeMapImpl createMaintainableArtefact() {
        return new ConceptSchemeMapImpl();
    }

    @Override
    protected ConceptSchemeMapImpl read(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        ConceptSchemeMapImpl conceptSchemeMap = super.read(reader);
        conceptSchemeMap.setItemMaps(new ArrayList<>(itemMaps));
        return conceptSchemeMap;
    }

    @Override
    protected void read(XMLStreamReader reader, ConceptSchemeMapImpl conceptSchemeMap) throws URISyntaxException, XMLStreamException {
        String localName = reader.getLocalName();
        switch (localName) {
            case XmlConstants.SOURCE:
                Optional.ofNullable(reader.getElementText())
                    .map(MaintainableArtefactReference::new)
                    .ifPresent(conceptSchemeMap::setSource);
                break;
            case XmlConstants.TARGET:
                Optional.ofNullable(reader.getElementText())
                    .map(MaintainableArtefactReference::new)
                    .ifPresent(conceptSchemeMap::setTarget);
                break;
            case XmlConstants.ITEM_MAP:
                itemMaps.add(getItemMap(reader, annotableReader));
                break;
            default:
                throw new IllegalArgumentException("CategorySchemeMap " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
        }

    }

    @Override
    protected void setAttributes(XMLStreamReader reader, ConceptSchemeMapImpl maintainableArtefact) throws XMLStreamException {
        XmlReaderUtils.setCommonAttributes(reader, maintainableArtefact);
    }

    @Override
    protected String getName() {
        return XmlConstants.CONCEPT_SCHEME_MAP;
    }

    @Override
    protected String getNames() {
        return XmlConstants.CONCEPT_SCHEME_MAPS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<ConceptSchemeMapImpl> artefacts) {
        artefact.getConceptSchemeMaps().addAll(artefacts);
    }

    @Override
    protected void clean() {
        itemMaps.clear();
    }
}
