package com.epam.jsdmx.xml30.structure.reader;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.CategorySchemeMapImpl;
import com.epam.jsdmx.infomodel.sdmx30.ItemMap;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactReference;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

public class CategorySchemeMapReader extends XmlReader<CategorySchemeMapImpl> implements ItemSchemeMapReader {

    private final List<ItemMap> itemMaps = new ArrayList<>();

    public CategorySchemeMapReader(AnnotableReader annotableReader,
                                   NameableReader nameableReader) {
        super(annotableReader, nameableReader);
    }

    @Override
    protected CategorySchemeMapImpl read(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        CategorySchemeMapImpl categorySchemeMap = super.read(reader);
        categorySchemeMap.setItemMaps(new ArrayList<>(itemMaps));
        return categorySchemeMap;
    }

    @Override
    protected void read(XMLStreamReader reader, CategorySchemeMapImpl categorySchemeMap) throws URISyntaxException, XMLStreamException {
        String localName = reader.getLocalName();
        switch (localName) {
            case XmlConstants.SOURCE:
                Optional.ofNullable(reader.getElementText())
                    .map(MaintainableArtefactReference::new)
                    .ifPresent(categorySchemeMap::setSource);
                break;
            case XmlConstants.TARGET:
                Optional.ofNullable(reader.getElementText())
                    .map(MaintainableArtefactReference::new)
                    .ifPresent(categorySchemeMap::setTarget);
                break;
            case XmlConstants.ITEM_MAP:
                itemMaps.add(getItemMap(reader, annotableReader));
                break;
            default:
                throw new IllegalArgumentException("CategorySchemeMap " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
        }
    }

    @Override
    protected CategorySchemeMapImpl createMaintainableArtefact() {
        return new CategorySchemeMapImpl();
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, CategorySchemeMapImpl maintainableArtefact) throws XMLStreamException {
        XmlReaderUtils.setCommonAttributes(reader, maintainableArtefact);
    }

    @Override
    protected String getName() {
        return XmlConstants.CATEGORY_SCHEME_MAP;
    }

    @Override
    protected String getNames() {
        return XmlConstants.CATEGORY_SCHEME_MAPS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<CategorySchemeMapImpl> artefacts) {
        artefact.getCategorySchemeMaps().addAll(artefacts);
    }

    @Override
    protected void clean() {
        itemMaps.clear();
    }
}
