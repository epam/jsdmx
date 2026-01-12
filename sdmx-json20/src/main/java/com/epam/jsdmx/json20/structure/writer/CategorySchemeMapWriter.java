package com.epam.jsdmx.json20.structure.writer;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.CategorySchemeMap;
import com.epam.jsdmx.infomodel.sdmx30.ItemMap;

import com.fasterxml.jackson.core.JsonGenerator;

public class CategorySchemeMapWriter extends MaintainableWriter<CategorySchemeMap> {

    private final AnnotableWriter annotableWriter;
    private final ItemMapWriter itemMapWriter;

    public CategorySchemeMapWriter(VersionableWriter versionableWriter,
                                   LinksWriter linksWriter,
                                   AnnotableWriter annotableWriter, ItemMapWriter itemMapWriter) {
        super(versionableWriter, linksWriter);
        this.annotableWriter = annotableWriter;
        this.itemMapWriter = itemMapWriter;
    }

    @Override
    protected void writeFields(JsonGenerator jsonGenerator, CategorySchemeMap categorySchemeMap) throws IOException {
        super.writeFields(jsonGenerator, categorySchemeMap);
        List<ItemMap> itemMaps = categorySchemeMap.getItemMaps();
        itemMapWriter.writeItemMaps(jsonGenerator, itemMaps, annotableWriter);
        itemMapWriter.writeSource(jsonGenerator, categorySchemeMap.getSource());
        itemMapWriter.writeTarget(jsonGenerator, categorySchemeMap.getTarget());
    }

    @Override
    protected Set<CategorySchemeMap> extractArtefacts(Artefacts artefacts) {
        return artefacts.getCategorySchemeMaps();
    }

    @Override
    protected String getArrayName() {
        return StructureUtils.CATEGORY_SCHEME_MAPS;
    }
}
