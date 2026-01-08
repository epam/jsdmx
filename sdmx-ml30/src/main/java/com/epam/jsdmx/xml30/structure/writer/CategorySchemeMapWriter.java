package com.epam.jsdmx.xml30.structure.writer;

import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.CategorySchemeMap;

public class CategorySchemeMapWriter extends XmlWriter<CategorySchemeMap> {

    private final ItemSchemeMapWriter itemSchemeMapWriter;

    public CategorySchemeMapWriter(NameableWriter nameableWriter,
                                   AnnotableWriter annotableWriter,
                                   CommonAttributesWriter commonAttributesWriter,
                                   LinksWriter linksWriter,
                                   ItemSchemeMapWriter itemSchemeMapWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
        this.itemSchemeMapWriter = itemSchemeMapWriter;
    }

    @Override
    protected void writeAttributes(CategorySchemeMap categorySchemeMap, XMLStreamWriter writer) throws XMLStreamException {
        commonAttributesWriter.writeAttributes(categorySchemeMap, writer);
    }

    @Override
    protected void writeCustomAttributeElements(CategorySchemeMap categorySchemeMap, XMLStreamWriter writer) throws XMLStreamException {
        itemSchemeMapWriter.writeSource(writer, categorySchemeMap.getSource(), XmlConstants.STRUCTURE + XmlConstants.SOURCE);
        itemSchemeMapWriter.writeTarget(writer, categorySchemeMap.getTarget(), XmlConstants.STRUCTURE + XmlConstants.TARGET);
        itemSchemeMapWriter.writeItems(writer, categorySchemeMap.getItemMaps(), annotableWriter);
    }

    @Override
    protected String getName() {
        return XmlConstants.CATEGORY_SCHEME_MAP;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.CATEGORY_SCHEME_MAPS;
    }

    @Override
    protected Set<CategorySchemeMap> extractArtefacts(Artefacts artefacts) {
        return artefacts.getCategorySchemeMaps();
    }
}
